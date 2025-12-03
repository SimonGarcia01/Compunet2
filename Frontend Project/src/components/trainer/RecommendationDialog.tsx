import { useState, useEffect } from "react";
import { useDispatch } from "react-redux";
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogFooter } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { Textarea } from "@/components/ui/textarea";
import { Label } from "@/components/ui/label";
import { Alert, AlertDescription } from "@/components/ui/alert";
import { AlertCircle } from "lucide-react";
import { createRecommendation, updateRecommendation } from "@/store/recommendations/thunk";
import type { AppDispatch } from "@/store/index";
import type { Recommendation } from "@/store/recommendations/recommendationsSlice";
import { toast } from "sonner";

interface RecommendationDialogProps {
  open: boolean;
  onClose: () => void;
  studentEmail: string;
  recommendation?: Recommendation;
}

export function RecommendationDialog({
  open,
  onClose,
  studentEmail,
  recommendation
}: RecommendationDialogProps) {
  const dispatch = useDispatch<AppDispatch>();
  const [content, setContent] = useState("");
  const [error, setError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  const maxLength = 1000;
  const isEditing = !!recommendation;

  useEffect(() => {
    if (recommendation) {
      setContent(recommendation.content);
    } else {
      setContent("");
    }
    setError("");
  }, [recommendation, open]);

  const handleSubmit = async () => {
    // Validations
    if (!content.trim()) {
      setError("El contenido de la recomendación es requerido");
      return;
    }

    if (content.length > maxLength) {
      setError(`El contenido no puede exceder ${maxLength} caracteres`);
      return;
    }

    setIsSubmitting(true);
    setError("");

    try {
      if (isEditing && recommendation) {
        await dispatch(updateRecommendation({
          recommendationId: recommendation.recommendationId,
          content: content.trim()
        })).unwrap();
        toast.success("Recomendación actualizada exitosamente");
      } else {
        await dispatch(createRecommendation({
          content: content.trim(),
          studentEmail
        })).unwrap();
        toast.success("Recomendación creada exitosamente");
      }
      onClose();
      setContent("");
    } catch (err) {
      setError(err instanceof Error ? err.message : "Error al guardar la recomendación");
      toast.error("Error al guardar la recomendación");
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <Dialog open={open} onOpenChange={onClose}>
      <DialogContent className="sm:max-w-[600px]">
        <DialogHeader>
          <DialogTitle>
            {isEditing ? "Editar Recomendación" : "Nueva Recomendación"}
          </DialogTitle>
        </DialogHeader>

        <div className="space-y-4 py-4">
          {error && (
            <Alert variant="destructive">
              <AlertCircle className="h-4 w-4" />
              <AlertDescription>{error}</AlertDescription>
            </Alert>
          )}

          <div className="space-y-2">
            <Label htmlFor="content">
              Contenido de la Recomendación
              <span className="text-destructive ml-1">*</span>
            </Label>
            <Textarea
              id="content"
              value={content}
              onChange={(e) => setContent(e.target.value)}
              placeholder="Escribe tu recomendación para el estudiante..."
              className="min-h-[200px] resize-none"
              maxLength={maxLength}
              aria-invalid={!!error}
              aria-describedby={error ? "content-error" : undefined}
            />
            <div className="flex justify-between text-sm text-muted-foreground">
              <span id="content-error" className="text-destructive">
                {error && "Este campo es requerido"}
              </span>
              <span className={content.length > maxLength ? "text-destructive" : ""}>
                {content.length} / {maxLength}
              </span>
            </div>
          </div>

          <div className="rounded-lg bg-muted p-4">
            <p className="text-sm text-muted-foreground">
              <strong>Estudiante:</strong> {studentEmail}
            </p>
          </div>
        </div>

        <DialogFooter>
          <Button variant="outline" onClick={onClose} disabled={isSubmitting}>
            Cancelar
          </Button>
          <Button onClick={handleSubmit} disabled={isSubmitting}>
            {isSubmitting ? "Guardando..." : isEditing ? "Actualizar" : "Crear"}
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
}
