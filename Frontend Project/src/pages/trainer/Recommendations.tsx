import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
// RoleBasedNavbar is now provided by AppLayout
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { Skeleton } from "@/components/ui/skeleton";
import { RecommendationDialog } from "@/components/trainer/RecommendationDialog";
import { RecommendationList } from "@/components/trainer/RecommendationList";
import { ProgressMetrics } from "@/components/trainer/ProgressMetrics";
import { ArrowLeft, Plus, AlertCircle, Lightbulb } from "lucide-react";
import {
  fetchStudentRecommendations,
  deleteRecommendation,
  analyzeStudentProgress
} from "@/store/recommendations/thunk";
import { setCurrentRecommendation, type Recommendation } from "@/store/recommendations/recommendationsSlice";
import type { RootState, AppDispatch } from "@/store/index";
import { toast } from "sonner";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog";

export default function Recommendations() {
  const { studentEmail } = useParams<{ studentEmail: string }>();
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();

  const {
    studentRecommendations,
    progressAnalysis,
    currentRecommendation,
    loading,
    error
  } = useSelector((state: RootState) => state.recommendations);

  const { selectedStudent } = useSelector((state: RootState) => state.trainer);

  const [dialogOpen, setDialogOpen] = useState(false);
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false);
  const [recommendationToDelete, setRecommendationToDelete] = useState<number | null>(null);

  const decodedEmail = studentEmail ? decodeURIComponent(studentEmail) : '';
  const recommendations = studentRecommendations[decodedEmail] || [];
  const analysis = progressAnalysis[decodedEmail];

  useEffect(() => {
    if (decodedEmail) {
      dispatch(fetchStudentRecommendations({ studentEmail: decodedEmail }));
      // analyzeStudentProgress puede fallar si no hay progreso o el estudiante no está asignado
      // No bloqueamos la UI si falla, solo mostramos análisis vacío
      dispatch(analyzeStudentProgress({ studentEmail: decodedEmail })).catch(() => {
        // Silenciosamente manejar el error - el análisis vacío se mostrará
        console.warn('No se pudo analizar el progreso del estudiante');
      });
    }
  }, [dispatch, decodedEmail]);

  const handleEdit = (recommendation: Recommendation) => {
    dispatch(setCurrentRecommendation(recommendation));
    setDialogOpen(true);
  };

  const handleDeleteClick = (id: number) => {
    setRecommendationToDelete(id);
    setDeleteDialogOpen(true);
  };

  const handleDeleteConfirm = async () => {
    if (recommendationToDelete) {
      try {
        await dispatch(deleteRecommendation(recommendationToDelete)).unwrap();
        toast.success("Recomendación eliminada exitosamente");
        if (decodedEmail) {
          dispatch(fetchStudentRecommendations({ studentEmail: decodedEmail }));
        }
      } catch (err) {
        toast.error("Error al eliminar la recomendación");
      }
    }
    setDeleteDialogOpen(false);
    setRecommendationToDelete(null);
  };

  const handleCloseDialog = () => {
    setDialogOpen(false);
    dispatch(setCurrentRecommendation(null));
    if (decodedEmail) {
      dispatch(fetchStudentRecommendations({ studentEmail: decodedEmail }));
    }
  };

  if (!decodedEmail) {
    return (
      <div className="space-y-6">
        <Alert variant="destructive">
          <AlertCircle className="h-4 w-4" />
          <AlertDescription>Email de estudiante no válido</AlertDescription>
        </Alert>
      </div>
    );
  }

  return (
    <div className="space-y-6">
        <Button
          variant="ghost"
          onClick={() => navigate('/app/trainer/students')}
          className="mb-6"
        >
          <ArrowLeft className="h-4 w-4 mr-2" />
          Volver a Mis Alumnos
        </Button>

        <div className="flex items-center justify-between mb-8">
          <div>
            <h1 className="text-3xl font-bold mb-2">
              Recomendaciones para {selectedStudent?.name || decodedEmail}
            </h1>
            <p className="text-muted-foreground">{decodedEmail}</p>
          </div>
          <Button onClick={() => setDialogOpen(true)}>
            <Plus className="h-4 w-4 mr-2" />
            Nueva Recomendación
          </Button>
        </div>

        {error && (
          <Alert variant="destructive" className="mb-6">
            <AlertCircle className="h-4 w-4" />
            <AlertDescription>{error}</AlertDescription>
          </Alert>
        )}

        <div className="space-y-6">
          {/* Progress Metrics */}
          <section>
            <h2 className="text-xl font-semibold mb-4">Análisis de Progreso</h2>
            <ProgressMetrics analysis={analysis} loading={loading} />
          </section>

          {/* Auto Recommendations */}
          {analysis && analysis.recommendations.length > 0 && (
            <Card>
              <CardHeader>
                <CardTitle className="flex items-center gap-2">
                  <Lightbulb className="h-5 w-5 text-primary" />
                  Sugerencias Automáticas
                </CardTitle>
              </CardHeader>
              <CardContent>
                <ul className="space-y-2">
                  {analysis.recommendations.map((rec, index) => (
                    <li key={index} className="flex items-start gap-2">
                      <span className="text-primary mt-1">•</span>
                      <span className="text-sm">{rec}</span>
                    </li>
                  ))}
                </ul>
              </CardContent>
            </Card>
          )}

          {/* Recommendations List */}
          <section>
            <h2 className="text-xl font-semibold mb-4">Recomendaciones Creadas</h2>
            {loading && recommendations.length === 0 ? (
              <div className="space-y-4">
                {[1, 2, 3].map((i) => (
                  <Skeleton key={i} className="h-[200px]" />
                ))}
              </div>
            ) : (
              <RecommendationList
                recommendations={recommendations}
                studentEmail={decodedEmail}
                onEdit={handleEdit}
                onDelete={handleDeleteClick}
              />
            )}
          </section>
        </div>

      <RecommendationDialog
        open={dialogOpen}
        onClose={handleCloseDialog}
        studentEmail={decodedEmail}
        recommendation={currentRecommendation || undefined}
      />

      <AlertDialog open={deleteDialogOpen} onOpenChange={setDeleteDialogOpen}>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>¿Estás seguro?</AlertDialogTitle>
            <AlertDialogDescription>
              Esta acción no se puede deshacer. La recomendación será eliminada permanentemente.
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel onClick={() => setRecommendationToDelete(null)}>
              Cancelar
            </AlertDialogCancel>
            <AlertDialogAction onClick={handleDeleteConfirm} className="bg-destructive text-destructive-foreground hover:bg-destructive/90">
              Eliminar
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </div>
  );
}
