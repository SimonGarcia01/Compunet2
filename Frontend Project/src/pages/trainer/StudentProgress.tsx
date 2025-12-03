import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
// RoleBasedNavbar is now provided by AppLayout
import { Button } from "@/components/ui/button";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { ProgressStats } from "@/components/trainer/ProgressStats";
import { ProgressChart } from "@/components/trainer/ProgressChart";
import { ProgressTable } from "@/components/trainer/ProgressTable";
import { StudentFilters } from "@/components/trainer/StudentFilters";
import { Skeleton } from "@/components/ui/skeleton";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { ArrowLeft, AlertCircle, MessageSquare, Plus } from "lucide-react";
import { fetchStudentRecommendations } from "@/store/recommendations/thunk";
import { fetchStudentWorkouts, fetchStudentProgress } from "@/store/trainer/thunk";
import { setFilters, clearFilters } from "@/store/trainer/trainerSlice";
import type { RootState, AppDispatch } from "@/store/index";
import type { ProgressFilters } from "@/store/trainer/trainerSlice";

export default function StudentProgress() {
  const { studentEmail } = useParams<{ studentEmail: string }>();
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  
  const { 
    selectedStudent,
    studentWorkouts,
    studentProgress,
    filters,
    loading,
    error 
  } = useSelector((state: RootState) => state.trainer);

  const { studentRecommendations } = useSelector((state: RootState) => state.recommendations);

  const decodedEmail = studentEmail ? decodeURIComponent(studentEmail) : '';
  const workouts = studentWorkouts[decodedEmail] || [];
  const progress = studentProgress[decodedEmail] || [];
  const recommendations = studentRecommendations[decodedEmail] || [];

  useEffect(() => {
    if (decodedEmail) {
      dispatch(fetchStudentWorkouts({ studentEmail: decodedEmail }));
      dispatch(fetchStudentProgress({ studentEmail: decodedEmail, filters }));
      dispatch(fetchStudentRecommendations({ studentEmail: decodedEmail }));
    }
  }, [dispatch, decodedEmail]);

  const handleApplyFilters = (newFilters: ProgressFilters) => {
    dispatch(setFilters(newFilters));
    if (decodedEmail) {
      dispatch(fetchStudentProgress({ studentEmail: decodedEmail, filters: newFilters }));
    }
  };

  const handleClearFilters = () => {
    dispatch(clearFilters());
    if (decodedEmail) {
      dispatch(fetchStudentProgress({ studentEmail: decodedEmail }));
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

        <div className="mb-8">
          <div className="flex items-center justify-between">
            <div>
              <h1 className="text-3xl font-bold mb-2">
                Progreso de {selectedStudent?.name || decodedEmail}
              </h1>
              <p className="text-muted-foreground">{decodedEmail}</p>
            </div>
            <Button onClick={() => navigate(`/app/trainer/students/${encodeURIComponent(decodedEmail)}/recommendations`)}>
              <MessageSquare className="h-4 w-4 mr-2" />
              Ver Recomendaciones
            </Button>
          </div>
        </div>

        {error && (
          <Alert variant="destructive" className="mb-6">
            <AlertCircle className="h-4 w-4" />
            <AlertDescription>{error}</AlertDescription>
          </Alert>
        )}

        <div className="space-y-6">
          {/* Filters */}
          <StudentFilters
            filters={filters}
            workouts={workouts}
            onApplyFilters={handleApplyFilters}
            onClearFilters={handleClearFilters}
          />

          {/* Stats */}
          {loading ? (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
              {[1, 2, 3, 4, 5, 6].map((i) => (
                <Skeleton key={i} className="h-[120px]" />
              ))}
            </div>
          ) : (
            <ProgressStats progress={progress} />
          )}

          {/* Recent Recommendations */}
          {recommendations.length > 0 && (
            <Card>
              <CardHeader>
                <CardTitle className="flex items-center justify-between">
                  <span>Recomendaciones Recientes</span>
                  <Button
                    variant="ghost"
                    size="sm"
                    onClick={() => navigate(`/app/trainer/students/${encodeURIComponent(decodedEmail)}/recommendations`)}
                  >
                    Ver todas
                  </Button>
                </CardTitle>
              </CardHeader>
              <CardContent>
                <div className="space-y-4">
                  {recommendations.slice(0, 3).map((rec) => (
                    <div key={rec.recommendationId} className="border-l-2 border-primary pl-4 py-2">
                      <p className="text-sm text-muted-foreground mb-1">
                        {new Date(rec.commentDate).toLocaleDateString('es-ES', { 
                          day: 'numeric', 
                          month: 'long', 
                          year: 'numeric' 
                        })}
                      </p>
                      <p className="text-sm">{rec.content}</p>
                    </div>
                  ))}
                </div>
              </CardContent>
            </Card>
          )}

          {/* Charts and Table */}
          <Tabs defaultValue="charts" className="w-full">
            <TabsList className="grid w-full grid-cols-2">
              <TabsTrigger value="charts">Gráficos</TabsTrigger>
              <TabsTrigger value="table">Registros Detallados</TabsTrigger>
            </TabsList>

            <TabsContent value="charts" className="space-y-4 mt-6">
              {loading ? (
                <Skeleton className="h-[400px]" />
              ) : progress.length === 0 ? (
                <div className="text-center py-12 text-muted-foreground">
                  <p>No hay datos de progreso para mostrar</p>
                  <p className="text-sm mt-2">Ajusta los filtros o verifica que el estudiante tenga registros</p>
                </div>
              ) : (
                <ProgressChart progress={progress} />
              )}
            </TabsContent>

            <TabsContent value="table" className="mt-6">
              {loading ? (
                <Skeleton className="h-[400px]" />
              ) : (
                <ProgressTable progress={progress} />
              )}
            </TabsContent>
          </Tabs>
        </div>
    </div>
  );
}
