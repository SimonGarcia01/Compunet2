import jsPDF from 'jspdf';
import type { ExerciseProgress } from '@/store/userProgress/userProgressSlice';
import type { WorkoutProgram } from '@/store/workoutPrograms/workoutProgramsSlice';
import type { Recommendation } from '@/store/recommendations/recommendationsSlice';
import { format } from 'date-fns';
import { es } from 'date-fns/locale';

interface ReportData {
  userName: string;
  userEmail: string;
  progressData?: ExerciseProgress[];
  routinesData?: WorkoutProgram[];
  recommendationsData?: Recommendation[];
  dateRange?: {
    startDate: string;
    endDate: string;
  };
}

export function generateProgressReport(data: ReportData) {
  const doc = new jsPDF();
  const pageWidth = doc.internal.pageSize.getWidth();
  const pageHeight = doc.internal.pageSize.getHeight();
  let yPosition = 20;

  // Header
  doc.setFontSize(20);
  doc.setFont('helvetica', 'bold');
  doc.text('Reporte de Progreso', pageWidth / 2, yPosition, { align: 'center' });
  yPosition += 10;

  doc.setFontSize(12);
  doc.setFont('helvetica', 'normal');
  doc.text(`Usuario: ${data.userName}`, 20, yPosition);
  yPosition += 7;
  doc.text(`Email: ${data.userEmail}`, 20, yPosition);
  yPosition += 7;
  doc.text(
    `Fecha de generación: ${format(new Date(), "PPP 'a las' HH:mm", { locale: es })}`,
    20,
    yPosition
  );
  yPosition += 10;

  if (data.dateRange) {
    doc.text(
      `Período: ${format(new Date(data.dateRange.startDate), "PPP", { locale: es })} - ${format(new Date(data.dateRange.endDate), "PPP", { locale: es })}`,
      20,
      yPosition
    );
    yPosition += 10;
  }

  // Resumen estadístico
  if (data.progressData && data.progressData.length > 0) {
    doc.setFontSize(16);
    doc.setFont('helvetica', 'bold');
    doc.text('Resumen Estadístico', 20, yPosition);
    yPosition += 10;

    doc.setFontSize(11);
    doc.setFont('helvetica', 'normal');

    const totalRecords = data.progressData.length;
    const totalCalories = data.progressData.reduce(
      (sum, p) => sum + (p.estimatedCaloriesBurnt || 0),
      0
    );
    const avgRpe =
      data.progressData.reduce((sum, p) => sum + (p.rpe || 0), 0) / totalRecords;
    const totalTime = data.progressData.reduce((sum, p) => sum + (p.timeMinutes || 0), 0);
    const uniqueDates = new Set(data.progressData.map((p) => p.recordDate)).size;

    doc.text(`Total de registros: ${totalRecords}`, 20, yPosition);
    yPosition += 7;
    doc.text(`Calorías totales quemadas: ${totalCalories.toFixed(0)} kcal`, 20, yPosition);
    yPosition += 7;
    doc.text(`RPE promedio: ${avgRpe.toFixed(1)}/10`, 20, yPosition);
    yPosition += 7;
    doc.text(`Tiempo total: ${totalTime} minutos`, 20, yPosition);
    yPosition += 7;
    doc.text(`Días activos: ${uniqueDates}`, 20, yPosition);
    yPosition += 10;

    // Tabla de registros
    if (yPosition > pageHeight - 60) {
      doc.addPage();
      yPosition = 20;
    }

    doc.setFontSize(16);
    doc.setFont('helvetica', 'bold');
    doc.text('Registros de Progreso', 20, yPosition);
    yPosition += 10;

    doc.setFontSize(9);
    doc.setFont('helvetica', 'bold');
    
    // Headers de tabla
    const startX = 20;
    doc.text('Fecha', startX, yPosition);
    doc.text('Rutina', startX + 40, yPosition);
    doc.text('Ejercicio', startX + 80, yPosition);
    doc.text('RPE', startX + 130, yPosition);
    doc.text('Calorías', startX + 150, yPosition);
    yPosition += 7;

    doc.setFont('helvetica', 'normal');
    doc.setFontSize(8);

    data.progressData
      .sort((a, b) => new Date(b.recordDate).getTime() - new Date(a.recordDate).getTime())
      .slice(0, 30) // Limitar a 30 registros para evitar páginas muy largas
      .forEach((progress) => {
        if (yPosition > pageHeight - 20) {
          doc.addPage();
          yPosition = 20;
        }

        const date = format(new Date(progress.recordDate), 'dd/MM/yyyy', { locale: es });
        const routine = progress.workoutProgram?.name || 'N/A';
        const exercise = progress.exercise?.name || 'N/A';
        const rpe = progress.rpe?.toString() || '-';
        const calories = progress.estimatedCaloriesBurnt?.toFixed(0) || '0';

        doc.text(date, startX, yPosition);
        doc.text(routine.substring(0, 20), startX + 40, yPosition);
        doc.text(exercise.substring(0, 20), startX + 80, yPosition);
        doc.text(rpe, startX + 130, yPosition);
        doc.text(calories, startX + 150, yPosition);
        yPosition += 6;
      });
  } else {
    doc.setFontSize(12);
    doc.text('No hay datos de progreso disponibles', 20, yPosition);
  }

  // Recomendaciones
  if (data.recommendationsData && data.recommendationsData.length > 0) {
    if (yPosition > pageHeight - 40) {
      doc.addPage();
      yPosition = 20;
    }

    doc.setFontSize(16);
    doc.setFont('helvetica', 'bold');
    doc.text('Recomendaciones', 20, yPosition);
    yPosition += 10;

    doc.setFontSize(10);
    doc.setFont('helvetica', 'normal');

    data.recommendationsData.slice(0, 10).forEach((rec) => {
      if (yPosition > pageHeight - 30) {
        doc.addPage();
        yPosition = 20;
      }

      const date = format(new Date(rec.commentDate), 'dd/MM/yyyy', { locale: es });
      doc.setFont('helvetica', 'bold');
      doc.text(`Fecha: ${date}`, 20, yPosition);
      yPosition += 6;

      if (rec.trainer) {
        doc.setFont('helvetica', 'italic');
        doc.setFontSize(9);
        doc.text(`De: ${rec.trainer.name || rec.trainer.email}`, 20, yPosition);
        yPosition += 5;
      }

      doc.setFont('helvetica', 'normal');
      doc.setFontSize(9);
      const lines = doc.splitTextToSize(rec.content, pageWidth - 40);
      doc.text(lines, 20, yPosition);
      yPosition += lines.length * 5 + 5;
    });
  }

  // Footer
  const totalPages = doc.getNumberOfPages();
  for (let i = 1; i <= totalPages; i++) {
    doc.setPage(i);
    doc.setFontSize(8);
    doc.setFont('helvetica', 'italic');
    doc.text(
      `Página ${i} de ${totalPages} - Gym Icesi`,
      pageWidth / 2,
      pageHeight - 10,
      { align: 'center' }
    );
  }

  return doc;
}

export function generateRoutinesReport(data: ReportData) {
  const doc = new jsPDF();
  const pageWidth = doc.internal.pageSize.getWidth();
  const pageHeight = doc.internal.pageSize.getHeight();
  let yPosition = 20;

  // Header
  doc.setFontSize(20);
  doc.setFont('helvetica', 'bold');
  doc.text('Reporte de Rutinas', pageWidth / 2, yPosition, { align: 'center' });
  yPosition += 10;

  doc.setFontSize(12);
  doc.setFont('helvetica', 'normal');
  doc.text(`Usuario: ${data.userName}`, 20, yPosition);
  yPosition += 7;
  doc.text(`Email: ${data.userEmail}`, 20, yPosition);
  yPosition += 7;
  doc.text(
    `Fecha de generación: ${format(new Date(), "PPP 'a las' HH:mm", { locale: es })}`,
    20,
    yPosition
  );
  yPosition += 15;

  if (data.routinesData && data.routinesData.length > 0) {
    doc.setFontSize(16);
    doc.setFont('helvetica', 'bold');
    doc.text(`Total de Rutinas: ${data.routinesData.length}`, 20, yPosition);
    yPosition += 10;

    data.routinesData.forEach((routine) => {
      if (yPosition > pageHeight - 50) {
        doc.addPage();
        yPosition = 20;
      }

      doc.setFontSize(14);
      doc.setFont('helvetica', 'bold');
      doc.text(routine.name, 20, yPosition);
      yPosition += 8;

      doc.setFontSize(10);
      doc.setFont('helvetica', 'normal');
      const descLines = doc.splitTextToSize(routine.description || 'Sin descripción', pageWidth - 40);
      doc.text(descLines, 20, yPosition);
      yPosition += descLines.length * 5 + 5;

      doc.setFontSize(9);
      doc.text(
        `Fecha de creación: ${format(new Date(routine.creationDate), 'PPP', { locale: es })}`,
        20,
        yPosition
      );
      yPosition += 6;
      doc.text(`Estado: ${routine.completed ? 'Completada' : 'En progreso'}`, 20, yPosition);
      yPosition += 6;
      doc.text(`Ejercicios: ${routine.exercises.length}`, 20, yPosition);
      yPosition += 8;

      if (routine.exercises.length > 0) {
        doc.setFontSize(9);
        doc.setFont('helvetica', 'bold');
        doc.text('Ejercicios:', 20, yPosition);
        yPosition += 6;

        doc.setFont('helvetica', 'normal');
        routine.exercises.slice(0, 10).forEach((exercise) => {
          if (yPosition > pageHeight - 20) {
            doc.addPage();
            yPosition = 20;
          }
          doc.text(
            `• ${exercise.exerciseName || 'Ejercicio'} - ${exercise.series} series x ${exercise.amount} repeticiones`,
            25,
            yPosition
          );
          yPosition += 5;
        });
      }

      yPosition += 10;
    });
  } else {
    doc.setFontSize(12);
    doc.text('No hay rutinas disponibles', 20, yPosition);
  }

  // Footer
  const totalPages = doc.getNumberOfPages();
  for (let i = 1; i <= totalPages; i++) {
    doc.setPage(i);
    doc.setFontSize(8);
    doc.setFont('helvetica', 'italic');
    doc.text(
      `Página ${i} de ${totalPages} - Gym Icesi`,
      pageWidth / 2,
      pageHeight - 10,
      { align: 'center' }
    );
  }

  return doc;
}

export function generateFullReport(data: ReportData) {
  const doc = new jsPDF();
  const pageWidth = doc.internal.pageSize.getWidth();
  const pageHeight = doc.internal.pageSize.getHeight();
  let yPosition = 20;

  // Portada
  doc.setFontSize(24);
  doc.setFont('helvetica', 'bold');
  doc.text('REPORTE COMPLETO', pageWidth / 2, pageHeight / 2 - 20, { align: 'center' });
  
  doc.setFontSize(16);
  doc.setFont('helvetica', 'normal');
  doc.text(data.userName, pageWidth / 2, pageHeight / 2, { align: 'center' });
  
  doc.setFontSize(12);
  doc.text(data.userEmail, pageWidth / 2, pageHeight / 2 + 10, { align: 'center' });
  
  doc.setFontSize(10);
  doc.text(
    format(new Date(), "PPP 'a las' HH:mm", { locale: es }),
    pageWidth / 2,
    pageHeight / 2 + 20,
    { align: 'center' }
  );

  // Agregar sección de progreso
  if (data.progressData && data.progressData.length > 0) {
    doc.addPage();
    yPosition = 20;

    doc.setFontSize(20);
    doc.setFont('helvetica', 'bold');
    doc.text('Progreso', pageWidth / 2, yPosition, { align: 'center' });
    yPosition += 15;

    doc.setFontSize(11);
    doc.setFont('helvetica', 'normal');

    const totalRecords = data.progressData.length;
    const totalCalories = data.progressData.reduce(
      (sum, p) => sum + (p.estimatedCaloriesBurnt || 0),
      0
    );
    const avgRpe =
      data.progressData.reduce((sum, p) => sum + (p.rpe || 0), 0) / totalRecords;
    const totalTime = data.progressData.reduce((sum, p) => sum + (p.timeMinutes || 0), 0);
    const uniqueDates = new Set(data.progressData.map((p) => p.recordDate)).size;

    doc.text(`Total de registros: ${totalRecords}`, 20, yPosition);
    yPosition += 7;
    doc.text(`Calorías totales quemadas: ${totalCalories.toFixed(0)} kcal`, 20, yPosition);
    yPosition += 7;
    doc.text(`RPE promedio: ${avgRpe.toFixed(1)}/10`, 20, yPosition);
    yPosition += 7;
    doc.text(`Tiempo total: ${totalTime} minutos`, 20, yPosition);
    yPosition += 7;
    doc.text(`Días activos: ${uniqueDates}`, 20, yPosition);
  }

  // Agregar sección de rutinas
  if (data.routinesData && data.routinesData.length > 0) {
    doc.addPage();
    yPosition = 20;

    doc.setFontSize(20);
    doc.setFont('helvetica', 'bold');
    doc.text('Rutinas', pageWidth / 2, yPosition, { align: 'center' });
    yPosition += 15;

    doc.setFontSize(11);
    doc.setFont('helvetica', 'normal');
    doc.text(`Total de rutinas: ${data.routinesData.length}`, 20, yPosition);
    yPosition += 10;

    data.routinesData.forEach((routine) => {
      if (yPosition > pageHeight - 50) {
        doc.addPage();
        yPosition = 20;
      }

      doc.setFontSize(14);
      doc.setFont('helvetica', 'bold');
      doc.text(routine.name, 20, yPosition);
      yPosition += 8;

      doc.setFontSize(10);
      doc.setFont('helvetica', 'normal');
      const descLines = doc.splitTextToSize(routine.description || 'Sin descripción', pageWidth - 40);
      doc.text(descLines, 20, yPosition);
      yPosition += descLines.length * 5 + 5;

      doc.setFontSize(9);
      doc.text(
        `Fecha: ${format(new Date(routine.creationDate), 'PPP', { locale: es })}`,
        20,
        yPosition
      );
      yPosition += 6;
      doc.text(`Estado: ${routine.completed ? 'Completada' : 'En progreso'}`, 20, yPosition);
      yPosition += 6;
      doc.text(`Ejercicios: ${routine.exercises.length}`, 20, yPosition);
      yPosition += 10;
    });
  }

  // Agregar sección de recomendaciones
  if (data.recommendationsData && data.recommendationsData.length > 0) {
    doc.addPage();
    yPosition = 20;

    doc.setFontSize(20);
    doc.setFont('helvetica', 'bold');
    doc.text('Recomendaciones', pageWidth / 2, yPosition, { align: 'center' });
    yPosition += 15;

    doc.setFontSize(10);
    doc.setFont('helvetica', 'normal');

    data.recommendationsData.slice(0, 10).forEach((rec) => {
      if (yPosition > pageHeight - 30) {
        doc.addPage();
        yPosition = 20;
      }

      const date = format(new Date(rec.commentDate), 'dd/MM/yyyy', { locale: es });
      doc.setFont('helvetica', 'bold');
      doc.text(`Fecha: ${date}`, 20, yPosition);
      yPosition += 6;

      if (rec.trainer) {
        doc.setFont('helvetica', 'italic');
        doc.setFontSize(9);
        doc.text(`De: ${rec.trainer.name || rec.trainer.email}`, 20, yPosition);
        yPosition += 5;
      }

      doc.setFont('helvetica', 'normal');
      doc.setFontSize(9);
      const lines = doc.splitTextToSize(rec.content, pageWidth - 40);
      doc.text(lines, 20, yPosition);
      yPosition += lines.length * 5 + 5;
    });
  }

  // Footer
  const totalPages = doc.getNumberOfPages();
  for (let i = 1; i <= totalPages; i++) {
    doc.setPage(i);
    doc.setFontSize(8);
    doc.setFont('helvetica', 'italic');
    doc.text(
      `Página ${i} de ${totalPages} - Gym Icesi`,
      pageWidth / 2,
      pageHeight - 10,
      { align: 'center' }
    );
  }

  return doc;
}

