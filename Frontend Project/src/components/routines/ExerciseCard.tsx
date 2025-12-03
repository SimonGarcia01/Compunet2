import { useState } from 'react';
import { Exercise } from '@/store/exercises/exercisesSlice';
import { Card, CardContent, CardFooter } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { Play, MoreVertical, ExternalLink } from 'lucide-react';
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuTrigger } from '@/components/ui/dropdown-menu';
import { ExerciseDetailsDialog } from './ExerciseDetailsDialog';

interface ExerciseCardProps {
  exercise: Exercise;
  onAdd?: (exercise: Exercise) => void;
}

export function ExerciseCard({ exercise, onAdd }: ExerciseCardProps) {
  const [showDetails, setShowDetails] = useState(false);
  const [isHovered, setIsHovered] = useState(false);

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

  const getVideoThumbnail = (url?: string) => {
    if (!url) return null;
    
    // YouTube
    const youtubeMatch = url.match(/((https:\/\/www.)?youtube.com\/(watch\?v=)?)(\w+|\W+)*/);
    if (youtubeMatch) {
      return `${youtubeMatch[0]}`;
    }
    
    // Vimeo
    const vimeoMatch = url.match(/vimeo\.com\/(\d+)/);
    if (vimeoMatch) {
      return `https://vumbnail.com/${vimeoMatch[1]}.jpg`;
    }
    
    return null;
  };

  const thumbnail = getVideoThumbnail(exercise.videoUrl);

  return (
    <>
      <Card
        className="overflow-hidden group hover:shadow-lg transition-all duration-300 border-2 hover:border-primary/20"
        onMouseEnter={() => setIsHovered(true)}
        onMouseLeave={() => setIsHovered(false)}
      >
        <div className="relative aspect-video bg-muted overflow-hidden">
          {thumbnail ? (
            <>
              <img
                src={'/images/fondo.png'}
                alt={exercise.name}
                className="w-full h-full object-cover transition-transform duration-300 group-hover:scale-110"
              />
              {isHovered && exercise.videoUrl && (
                <div className="absolute inset-0 bg-black/60 flex items-center justify-center backdrop-blur-sm">
                  <Button
                    size="lg"
                    className="rounded-full w-16 h-16"
                    onClick={() => window.open(thumbnail)}
                  >
                    <Play className="h-6 w-6" />
                  </Button>
                </div>
              )}
            </>
          ) : (
            <div className="w-full h-full flex items-center justify-center">
              <div className="text-center text-muted-foreground">
                <Card>
                  <img src='/images/fondo.png'/>
                </Card>
              </div>
            </div>
          )}
        </div>

        <CardContent className="p-4">
          <div className="flex items-start justify-between gap-2 mb-3">
            <h3 className="font-bold text-lg line-clamp-1">{exercise.name}</h3>
            <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <Button variant="ghost" size="icon" className="h-8 w-8 shrink-0">
                  <MoreVertical className="h-4 w-4" />
                </Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent align="end">
                <DropdownMenuItem onClick={() => setShowDetails(true)}>
                  Ver detalles
                </DropdownMenuItem>
                {exercise.videoUrl && (
                  <DropdownMenuItem onClick={() => window.open(exercise.videoUrl, '_blank')}>
                    <ExternalLink className="h-4 w-4 mr-2" />
                    Abrir video
                  </DropdownMenuItem>
                )}
                {onAdd && (
                  <DropdownMenuItem onClick={() => onAdd(exercise)}>
                    Añadir con valores por defecto
                  </DropdownMenuItem>
                )}
              </DropdownMenuContent>
            </DropdownMenu>
          </div>

          <p className="text-sm text-muted-foreground line-clamp-2 mb-3">
            {exercise.description}
          </p>

          <div className="flex flex-wrap gap-2">
            <Badge className={typeColors[exercise.type]} variant="outline">
              {exercise.type}
            </Badge>
            <Badge className={difficultyColors[exercise.difficulty]} variant="secondary">
              {exercise.difficulty}
            </Badge>
            <Badge variant="outline">
              {exercise.progressUnit === 'reps' ? 'Repeticiones' : exercise.progressUnit === 'min' ? 'Minutos' : 'Kilómetros'}
            </Badge>
          </div>
        </CardContent>

        {onAdd && (
          <CardFooter className="p-4 pt-0">
            <Button
              className="w-full"
              onClick={() => onAdd(exercise)}
              variant="secondary"
            >
              Agregar al programa
            </Button>
          </CardFooter>
        )}
      </Card>

      <ExerciseDetailsDialog
        exercise={exercise}
        open={showDetails}
        onClose={() => setShowDetails(false)}
        onAdd={onAdd}
      />
    </>
  );
}
