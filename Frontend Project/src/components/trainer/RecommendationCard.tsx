import { Card, CardContent, CardHeader } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Pencil, Trash2, Calendar } from "lucide-react";
import { format } from "date-fns";
import { es } from "date-fns/locale";
import type { Recommendation } from "@/store/recommendations/recommendationsSlice";

interface RecommendationCardProps {
  recommendation: Recommendation;
  onEdit: () => void;
  onDelete: () => void;
}

export function RecommendationCard({ recommendation, onEdit, onDelete }: RecommendationCardProps) {
  const formattedDate = format(new Date(recommendation.commentDate), "d 'de' MMMM, yyyy", { locale: es });

  return (
    <Card className="hover:shadow-md transition-shadow">
      <CardHeader className="pb-3">
        <div className="flex items-start justify-between gap-4">
          <div className="flex-1 space-y-1">
            <div className="flex items-center gap-2 text-sm text-muted-foreground">
              <Calendar className="h-4 w-4" />
              <span>{formattedDate}</span>
            </div>
            {recommendation.trainer && (
              <p className="text-sm text-muted-foreground">
                Por: {recommendation.trainer.name || recommendation.trainer.email}
              </p>
            )}
          </div>
          <div className="flex gap-2">
            <Button
              variant="ghost"
              size="icon"
              onClick={onEdit}
              aria-label="Editar recomendación"
            >
              <Pencil className="h-4 w-4" />
            </Button>
            <Button
              variant="ghost"
              size="icon"
              onClick={onDelete}
              aria-label="Eliminar recomendación"
              className="text-destructive hover:text-destructive"
            >
              <Trash2 className="h-4 w-4" />
            </Button>
          </div>
        </div>
      </CardHeader>
      <CardContent>
        <div className="space-y-3">
          <p className="text-sm leading-relaxed whitespace-pre-wrap">{recommendation.content}</p>
          
          {recommendation.generalProgress && (
            <div className="pt-3 border-t">
              <Badge variant="secondary" className="text-xs">
                Progreso: {recommendation.generalProgress.type} - {recommendation.generalProgress.percentage}%
              </Badge>
            </div>
          )}
        </div>
      </CardContent>
    </Card>
  );
}
