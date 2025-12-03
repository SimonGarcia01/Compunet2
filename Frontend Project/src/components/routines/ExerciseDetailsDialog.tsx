import { Exercise } from '@/store/exercises/exercisesSlice';
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogFooter } from '@/components/ui/dialog';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';
import { Separator } from '@/components/ui/separator';
import { ExternalLink, Flame } from 'lucide-react';

interface ExerciseDetailsDialogProps {
  exercise: Exercise;
  open: boolean;
  onClose: () => void;
  onAdd?: (exercise: Exercise) => void;
}

export function ExerciseDetailsDialog({ exercise, open, onClose, onAdd }: ExerciseDetailsDialogProps) {
  const getEmbedUrl = (url?: string) => {
    if (!url) return null;
    
    const youtubeMatch = url.match(/(?:youtube\.com\/watch\?v=|youtu\.be\/)([^&]+)/);
    if (youtubeMatch) {
      return `https://www.youtube.com/embed/${youtubeMatch[1]}?autoplay=1&mute=1`;
    }
    
    const vimeoMatch = url.match(/vimeo\.com\/(\d+)/);
    if (vimeoMatch) {
      return `https://player.vimeo.com/video/${vimeoMatch[1]}?autoplay=1&muted=1`;
    }
    
    return url;
  };

  const embedUrl = getEmbedUrl(exercise.videoUrl);

  const typeColors = {
    cardio: 'bg-red-500/10 text-red-500 border-red-500/20',
    fuerza: 'bg-blue-500/10 text-blue-500 border-blue-500/20',
    movilidad: 'bg-green-500/10 text-green-500 border-green-500/20',
  };

  const difficultyColors = {
    baja: 'bg-green-500/10 text-green-500',
    media: 'bg-yellow-500/10 text-yellow-500',
    alta: 'bg-red-500/10 text-red-500',
  };

  return (
    <Dialog open={open} onOpenChange={onClose}>
      <DialogContent className="max-w-3xl max-h-[90vh] overflow-y-auto">
        <DialogHeader>
          <DialogTitle className="text-2xl">{exercise.name}</DialogTitle>
        </DialogHeader>

        {embedUrl && (
          <div className="relative aspect-video bg-muted rounded-lg overflow-hidden">
            <img
                src={'/images/fondo.png'}
                width={''}
                alt={exercise.name}
                className="w-full h-full object-cover transition-transform duration-300 group-hover:scale-110"
            />
          </div>
        )}

        <div className="space-y-4">
          <div className="flex flex-wrap gap-2">
            <Badge className={typeColors[exercise.type]} variant="outline">
              {exercise.type}
            </Badge>
            <Badge className={difficultyColors[exercise.difficulty]} variant="secondary">
              Dificultad: {exercise.difficulty}
            </Badge>
            <Badge variant="outline">
              {exercise.progressUnit === 'reps' ? 'Repeticiones' : exercise.progressUnit === 'min' ? 'Minutos' : 'Kilómetros'}
            </Badge>
            {exercise.estimatedUnitaryCaloriesBurnt > 0 && (
              <Badge variant="outline" className="gap-1">
                <Flame className="h-3 w-3" />
                ~{exercise.estimatedUnitaryCaloriesBurnt} cal/unidad
              </Badge>
            )}
          </div>

          <Separator />

          <div>
            <h3 className="font-bold mb-2">Descripción</h3>
            <p className="text-muted-foreground whitespace-pre-wrap">
              {exercise.description}
            </p>
          </div>

          {exercise.videoUrl && (
            <>
              <Separator />
              <div>
                <Button
                  variant="outline"
                  className="gap-2"
                  onClick={() => window.open(exercise.videoUrl, '_blank')}
                >
                  <ExternalLink className="h-4 w-4" />
                  Abrir video en nueva pestaña
                </Button>
              </div>
            </>
          )}
        </div>

        <DialogFooter>
          <Button variant="outline" onClick={onClose}>
            Cerrar
          </Button>
          {onAdd && (
            <Button onClick={() => { onAdd(exercise); onClose(); }}>
              Agregar al programa
            </Button>
          )}
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
}
