import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { Skeleton } from "@/components/ui/skeleton";
import { Lightbulb, AlertCircle, Calendar, User } from "lucide-react";
import { format } from "date-fns";
import { es } from "date-fns/locale";
import type { RootState, AppDispatch } from "@/store/index";
import { fetchMyRecommendations } from "@/store/recommendations/thunk";
import type { Recommendation } from "@/store/recommendations/recommendationsSlice";

export default function UserRecommendations() {
  const dispatch = useDispatch<AppDispatch>();
  const { myRecommendations, loading, error } = useSelector((state: RootState) => state.recommendations);

  useEffect(() => {
    dispatch(fetchMyRecommendations());
  }, [dispatch]);

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold mb-2 flex items-center gap-3">
          <Lightbulb className="h-8 w-8 text-primary" />
          Mis Recomendaciones
        </h1>
        <p className="text-muted-foreground">
          Recomendaciones personalizadas de tus entrenadores basadas en tu progreso
        </p>
      </div>

      {error && (
        <Alert variant="destructive">
          <AlertCircle className="h-4 w-4" />
          <AlertDescription>{error}</AlertDescription>
        </Alert>
      )}

      {loading && myRecommendations.length === 0 ? (
        <div className="space-y-4">
          {[1, 2, 3].map((i) => (
            <Skeleton key={i} className="h-[200px]" />
          ))}
        </div>
      ) : myRecommendations.length === 0 ? (
        <Card>
          <CardContent className="py-12 text-center">
            <Lightbulb className="h-16 w-16 text-muted-foreground mx-auto mb-4" />
            <h3 className="text-xl font-semibold mb-2">No hay recomendaciones</h3>
            <p className="text-muted-foreground">
              Aún no tienes recomendaciones. Tu entrenador te enviará recomendaciones basadas en tu progreso.
            </p>
          </CardContent>
        </Card>
      ) : (
        <div className="space-y-4">
          {myRecommendations.map((recommendation: Recommendation) => (
            <Card key={recommendation.recommendationId} className="hover:shadow-lg transition-shadow">
              <CardHeader>
                <div className="flex items-start justify-between">
                  <div className="flex-1">
                    <CardTitle className="flex items-center gap-2 mb-2">
                      <Lightbulb className="h-5 w-5 text-primary" />
                      Recomendación
                    </CardTitle>
                    {recommendation.trainer && (
                      <div className="flex items-center gap-2 text-sm text-muted-foreground mb-2">
                        <User className="h-4 w-4" />
                        <span>
                          De: {recommendation.trainer.name || recommendation.trainer.email}
                        </span>
                      </div>
                    )}
                    {recommendation.commentDate && (
                      <div className="flex items-center gap-2 text-sm text-muted-foreground">
                        <Calendar className="h-4 w-4" />
                        <span>
                          {format(new Date(recommendation.commentDate), "PPP", { locale: es })}
                        </span>
                      </div>
                    )}
                  </div>
                </div>
              </CardHeader>
              <CardContent>
                <p className="text-base leading-relaxed whitespace-pre-wrap">
                  {recommendation.content}
                </p>
                {recommendation.generalProgress && (
                  <div className="mt-4 pt-4 border-t">
                    <p className="text-sm text-muted-foreground">
                      Tipo: {recommendation.generalProgress.type} - 
                      Progreso: {recommendation.generalProgress.percentage}% ({recommendation.generalProgress.daysOrWeeks})
                    </p>
                  </div>
                )}
              </CardContent>
            </Card>
          ))}
        </div>
      )}
    </div>
  );
}

