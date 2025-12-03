import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Calendar } from "@/components/ui/calendar";
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover";
import { Label } from "@/components/ui/label";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { Skeleton } from "@/components/ui/skeleton";
import { FileText, Download, Calendar as CalendarIcon, AlertCircle, Loader2 } from "lucide-react";
import { format } from "date-fns";
import { es } from "date-fns/locale";
import { cn } from "@/lib/utils";
import type { RootState, AppDispatch } from "@/store/index";
import { fetchMyProgress } from "@/store/userProgress/thunk";
import { fetchMyPrograms } from "@/store/workoutPrograms/thunk";
import { fetchMyRecommendations } from "@/store/recommendations/thunk";
import { generateProgressReport, generateRoutinesReport, generateFullReport } from "@/utils/pdfGenerator";
import { toast } from "sonner";

type ReportType = "progress" | "routines" | "full";

export default function Reports() {
  const dispatch = useDispatch<AppDispatch>();
  const { user } = useSelector((s: RootState) => s.auth);
  const { items: progressData, loading: progressLoading } = useSelector((s: RootState) => s.userProgress);
  const { items: routinesData, loading: routinesLoading } = useSelector((s: RootState) => s.workoutPrograms);
  const { myRecommendations, loading: recommendationsLoading } = useSelector((s: RootState) => s.recommendations);

  const [startDate, setStartDate] = useState<Date | undefined>(undefined);
  const [endDate, setEndDate] = useState<Date | undefined>(undefined);
  const [generating, setGenerating] = useState(false);

  useEffect(() => {
    dispatch(fetchMyProgress());
    dispatch(fetchMyPrograms());
    dispatch(fetchMyRecommendations());
  }, [dispatch]);

  const loading = progressLoading || routinesLoading || recommendationsLoading;

  const handleGenerateReport = async (type: ReportType) => {
    if (!user) {
      toast.error("No se pudo obtener información del usuario");
      return;
    }

    setGenerating(true);
    try {
      // Filtrar datos por rango de fechas si se especificó
      let filteredProgress = progressData;
      if (startDate && endDate) {
        filteredProgress = progressData.filter((p) => {
          const recordDate = new Date(p.recordDate);
          return recordDate >= startDate && recordDate <= endDate;
        });
      }

      const reportData = {
        userName: user.name || user.email.split("@")[0],
        userEmail: user.email,
        progressData: filteredProgress,
        routinesData: routinesData,
        recommendationsData: myRecommendations,
        dateRange: startDate && endDate
          ? {
              startDate: startDate.toISOString(),
              endDate: endDate.toISOString(),
            }
          : undefined,
      };

      let doc;
      let filename;

      switch (type) {
        case "progress":
          doc = generateProgressReport(reportData);
          filename = `reporte-progreso-${format(new Date(), "yyyy-MM-dd")}.pdf`;
          break;
        case "routines":
          doc = generateRoutinesReport(reportData);
          filename = `reporte-rutinas-${format(new Date(), "yyyy-MM-dd")}.pdf`;
          break;
        case "full":
          doc = generateFullReport(reportData);
          filename = `reporte-completo-${format(new Date(), "yyyy-MM-dd")}.pdf`;
          break;
      }

      doc.save(filename);
      toast.success("Reporte generado exitosamente");
    } catch (error) {
      console.error("Error generando reporte:", error);
      toast.error("Error al generar el reporte");
    } finally {
      setGenerating(false);
    }
  };

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold mb-2 flex items-center gap-3">
          <FileText className="h-8 w-8 text-primary" />
          Reportes PDF
        </h1>
        <p className="text-muted-foreground">
          Genera reportes detallados de tu progreso, rutinas y recomendaciones
        </p>
      </div>

      {/* Filtros de fecha */}
      <Card>
        <CardHeader>
          <CardTitle>Filtros de Fecha (Opcional)</CardTitle>
          <CardDescription>
            Selecciona un rango de fechas para filtrar los datos del reporte
          </CardDescription>
        </CardHeader>
        <CardContent>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div className="space-y-2">
              <Label>Fecha de inicio</Label>
              <Popover>
                <PopoverTrigger asChild>
                  <Button
                    variant="outline"
                    className={cn(
                      "w-full justify-start text-left font-normal",
                      !startDate && "text-muted-foreground"
                    )}
                  >
                    <CalendarIcon className="mr-2 h-4 w-4" />
                    {startDate ? format(startDate, "PPP", { locale: es }) : "Seleccionar fecha"}
                  </Button>
                </PopoverTrigger>
                <PopoverContent className="w-auto p-0">
                  <Calendar
                    mode="single"
                    selected={startDate}
                    onSelect={setStartDate}
                    initialFocus
                  />
                </PopoverContent>
              </Popover>
            </div>
            <div className="space-y-2">
              <Label>Fecha de fin</Label>
              <Popover>
                <PopoverTrigger asChild>
                  <Button
                    variant="outline"
                    className={cn(
                      "w-full justify-start text-left font-normal",
                      !endDate && "text-muted-foreground"
                    )}
                  >
                    <CalendarIcon className="mr-2 h-4 w-4" />
                    {endDate ? format(endDate, "PPP", { locale: es }) : "Seleccionar fecha"}
                  </Button>
                </PopoverTrigger>
                <PopoverContent className="w-auto p-0">
                  <Calendar
                    mode="single"
                    selected={endDate}
                    onSelect={setEndDate}
                    initialFocus
                  />
                </PopoverContent>
              </Popover>
            </div>
          </div>
          {(startDate || endDate) && (
            <Button
              variant="ghost"
              size="sm"
              className="mt-2"
              onClick={() => {
                setStartDate(undefined);
                setEndDate(undefined);
              }}
            >
              Limpiar filtros
            </Button>
          )}
        </CardContent>
      </Card>

      {/* Tipos de reporte */}
      <div className="grid gap-4 md:grid-cols-3">
        <Card className="hover:shadow-lg transition-shadow">
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <FileText className="h-5 w-5 text-blue-600" />
              Reporte de Progreso
            </CardTitle>
            <CardDescription>
              Incluye estadísticas y registros detallados de tu progreso
            </CardDescription>
          </CardHeader>
          <CardContent>
            {loading ? (
              <Skeleton className="h-10 w-full" />
            ) : (
              <Button
                className="w-full"
                onClick={() => handleGenerateReport("progress")}
                disabled={generating || progressData.length === 0}
              >
                {generating ? (
                  <>
                    <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                    Generando...
                  </>
                ) : (
                  <>
                    <Download className="mr-2 h-4 w-4" />
                    Generar PDF
                  </>
                )}
              </Button>
            )}
            {!loading && progressData.length === 0 && (
              <Alert className="mt-2">
                <AlertCircle className="h-4 w-4" />
                <AlertDescription className="text-xs">
                  No hay datos de progreso disponibles
                </AlertDescription>
              </Alert>
            )}
          </CardContent>
        </Card>

        <Card className="hover:shadow-lg transition-shadow">
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <FileText className="h-5 w-5 text-green-600" />
              Reporte de Rutinas
            </CardTitle>
            <CardDescription>
              Lista completa de tus rutinas y ejercicios
            </CardDescription>
          </CardHeader>
          <CardContent>
            {loading ? (
              <Skeleton className="h-10 w-full" />
            ) : (
              <Button
                className="w-full"
                variant="outline"
                onClick={() => handleGenerateReport("routines")}
                disabled={generating || routinesData.length === 0}
              >
                {generating ? (
                  <>
                    <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                    Generando...
                  </>
                ) : (
                  <>
                    <Download className="mr-2 h-4 w-4" />
                    Generar PDF
                  </>
                )}
              </Button>
            )}
            {!loading && routinesData.length === 0 && (
              <Alert className="mt-2">
                <AlertCircle className="h-4 w-4" />
                <AlertDescription className="text-xs">
                  No hay rutinas disponibles
                </AlertDescription>
              </Alert>
            )}
          </CardContent>
        </Card>

        <Card className="hover:shadow-lg transition-shadow border-primary">
          <CardHeader>
            <CardTitle className="flex items-center gap-2">
              <FileText className="h-5 w-5 text-purple-600" />
              Reporte Completo
            </CardTitle>
            <CardDescription>
              Reporte completo con progreso, rutinas y recomendaciones
            </CardDescription>
          </CardHeader>
          <CardContent>
            {loading ? (
              <Skeleton className="h-10 w-full" />
            ) : (
              <Button
                className="w-full"
                variant="default"
                onClick={() => handleGenerateReport("full")}
                disabled={generating}
              >
                {generating ? (
                  <>
                    <Loader2 className="mr-2 h-4 w-4 animate-spin" />
                    Generando...
                  </>
                ) : (
                  <>
                    <Download className="mr-2 h-4 w-4" />
                    Generar PDF Completo
                  </>
                )}
              </Button>
            )}
          </CardContent>
        </Card>
      </div>

      {/* Información adicional */}
      <Card>
        <CardHeader>
          <CardTitle>Información de los Reportes</CardTitle>
        </CardHeader>
        <CardContent className="space-y-2 text-sm text-muted-foreground">
          <p>
            • <strong>Reporte de Progreso:</strong> Incluye estadísticas generales, registros de ejercicios,
            calorías quemadas, RPE promedio y días activos.
          </p>
          <p>
            • <strong>Reporte de Rutinas:</strong> Lista todas tus rutinas con sus ejercicios, series y
            repeticiones.
          </p>
          <p>
            • <strong>Reporte Completo:</strong> Combina progreso, rutinas y recomendaciones en un solo
            documento.
          </p>
          <p className="mt-4 text-xs">
            Los reportes se generan con los datos disponibles al momento de la descarga. Si seleccionas un
            rango de fechas, solo se incluirán los datos dentro de ese período.
          </p>
        </CardContent>
      </Card>
    </div>
  );
}

