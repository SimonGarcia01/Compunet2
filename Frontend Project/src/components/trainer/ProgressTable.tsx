import { useState } from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { ChevronDown, ChevronUp } from "lucide-react";
import type { ExerciseProgress } from "@/store/trainer/trainerSlice";
import { format } from "date-fns";
import { es } from "date-fns/locale";

interface ProgressTableProps {
  progress: ExerciseProgress[];
}

type SortField = 'date' | 'workout' | 'exercise' | 'rpe' | 'calories';
type SortDirection = 'asc' | 'desc';

export function ProgressTable({ progress }: ProgressTableProps) {
  const [sortField, setSortField] = useState<SortField>('date');
  const [sortDirection, setSortDirection] = useState<SortDirection>('desc');
  const [expandedRows, setExpandedRows] = useState<Set<string>>(new Set());

  const handleSort = (field: SortField) => {
    if (sortField === field) {
      setSortDirection(sortDirection === 'asc' ? 'desc' : 'asc');
    } else {
      setSortField(field);
      setSortDirection('desc');
    }
  };

  const sortedProgress = [...progress].sort((a, b) => {
    let comparison = 0;
    
    switch (sortField) {
      case 'date':
        comparison = new Date(a.recordDate).getTime() - new Date(b.recordDate).getTime();
        break;
      case 'workout':
        comparison = (a.workoutProgram?.name || '').localeCompare(b.workoutProgram?.name || '');
        break;
      case 'exercise':
        comparison = (a.exercise?.name || '').localeCompare(b.exercise?.name || '');
        break;
      case 'rpe':
        comparison = (a.rpe || 0) - (b.rpe || 0);
        break;
      case 'calories':
        comparison = (a.estimatedCaloriesBurnt || 0) - (b.estimatedCaloriesBurnt || 0);
        break;
    }
    
    return sortDirection === 'asc' ? comparison : -comparison;
  });

  const toggleRow = (id: string) => {
    const newExpanded = new Set(expandedRows);
    if (newExpanded.has(id)) {
      newExpanded.delete(id);
    } else {
      newExpanded.add(id);
    }
    setExpandedRows(newExpanded);
  };

  const SortButton = ({ field, children }: { field: SortField; children: React.ReactNode }) => (
    <Button
      variant="ghost"
      size="sm"
      className="h-8 px-2"
      onClick={() => handleSort(field)}
    >
      {children}
      {sortField === field && (
        sortDirection === 'asc' ? <ChevronUp className="ml-1 h-4 w-4" /> : <ChevronDown className="ml-1 h-4 w-4" />
      )}
    </Button>
  );

  return (
    <div className="rounded-md border">
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>
              <SortButton field="date">Fecha</SortButton>
            </TableHead>
            <TableHead>
              <SortButton field="workout">Rutina</SortButton>
            </TableHead>
            <TableHead>
              <SortButton field="exercise">Ejercicio</SortButton>
            </TableHead>
            <TableHead>Tipo</TableHead>
            <TableHead>Progreso</TableHead>
            <TableHead>
              <SortButton field="rpe">RPE</SortButton>
            </TableHead>
            <TableHead>
              <SortButton field="calories">Calorías</SortButton>
            </TableHead>
            <TableHead>Notas</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {sortedProgress.length === 0 ? (
            <TableRow>
              <TableCell colSpan={8} className="text-center text-muted-foreground">
                No hay registros de progreso
              </TableCell>
            </TableRow>
          ) : (
            sortedProgress.map((record) => (
              <TableRow key={record.progressId}>
                <TableCell className="whitespace-nowrap">
                  {format(new Date(record.recordDate), "d MMM yyyy", { locale: es })}
                </TableCell>
                <TableCell>{record.workoutProgram?.name || '-'}</TableCell>
                <TableCell>{record.exercise?.name || '-'}</TableCell>
                <TableCell>
                  <Badge variant="outline">
                    {record.exercise?.type || 'N/A'}
                  </Badge>
                </TableCell>
                <TableCell>
                  {record.repetitions && `${record.repetitions} reps`}
                  {record.timeMinutes && `${record.timeMinutes} min`}
                  {record.distanceKm && `${record.distanceKm} km`}
                </TableCell>
                <TableCell>
                  {record.rpe && (
                    <Badge variant={record.rpe >= 8 ? "destructive" : record.rpe >= 5 ? "default" : "secondary"}>
                      {record.rpe}/10
                    </Badge>
                  )}
                </TableCell>
                <TableCell>{record.estimatedCaloriesBurnt || '-'}</TableCell>
                <TableCell className="max-w-[200px]">
                  {record.notes && (
                    <div>
                      <Button
                        variant="ghost"
                        size="sm"
                        onClick={() => toggleRow(record.progressId)}
                        className="h-auto p-0 text-xs"
                      >
                        {expandedRows.has(record.progressId) ? 'Ocultar' : 'Ver notas'}
                      </Button>
                      {expandedRows.has(record.progressId) && (
                        <p className="mt-1 text-xs text-muted-foreground">{record.notes}</p>
                      )}
                    </div>
                  )}
                </TableCell>
              </TableRow>
            ))
          )}
        </TableBody>
      </Table>
    </div>
  );
}
