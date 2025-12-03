import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch, RootState } from '@/store/index';
import { fetchExercises } from '@/store/exercises/thunk';
import { setFilters, setSortBy, clearFilters } from '@/store/exercises/exercisesSlice';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { Badge } from '@/components/ui/badge';
import { Checkbox } from '@/components/ui/checkbox';
import { Label } from '@/components/ui/label';
import { Search, Filter, Plus, X } from 'lucide-react';
import { ExerciseCard } from '@/components/routines/ExerciseCard';
import { CustomExerciseDialog } from '@/components/routines/CustomExerciseDialog';
import { Skeleton } from '@/components/ui/skeleton';

export default function ExerciseCatalog() {
  const dispatch = useDispatch<AppDispatch>();
  const { filteredItems, loading, filters, sortBy } = useSelector((state: RootState) => state.exercises);
  const [searchTerm, setSearchTerm] = useState('');
  const [showFilters, setShowFilters] = useState(false);
  const [showCustomDialog, setShowCustomDialog] = useState(false);

  useEffect(() => {
    dispatch(fetchExercises());
  }, [dispatch]);

  useEffect(() => {
    const timer = setTimeout(() => {
      dispatch(setFilters({ search: searchTerm }));
    }, 300);

    return () => clearTimeout(timer);
  }, [searchTerm, dispatch]);

  const predefinedExercises = filteredItems.filter(e => !e.isCustom);
  const customExercises = filteredItems.filter(e => e.isCustom);

  const handleClearFilters = () => {
    setSearchTerm('');
    dispatch(clearFilters());
  };

  const hasActiveFilters = filters.type || filters.difficulty || filters.progressUnit || filters.hasVideo || filters.search;

  return (
    <div className="min-h-screen bg-background">
      <div className="container mx-auto px-4 py-8">
        {/* Header */}
        <div className="mb-8">
          <h1 className="text-4xl font-black mb-2">Catálogo de Ejercicios</h1>
          <p className="text-muted-foreground">Explora y gestiona ejercicios para tus rutinas</p>
        </div>

        {/* Barra de búsqueda y acciones */}
        <div className="flex flex-col sm:flex-row gap-4 mb-6">
          <div className="relative flex-1">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground" />
            <Input
              placeholder="Buscar ejercicios..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="pl-10"
            />
          </div>
          <div className="flex gap-2">
            <Button
              variant="outline"
              onClick={() => setShowFilters(!showFilters)}
              className="gap-2"
            >
              <Filter className="h-4 w-4" />
              Filtros
              {hasActiveFilters && (
                <Badge variant="secondary" className="ml-1 h-5 w-5 rounded-full p-0 flex items-center justify-center">
                  !
                </Badge>
              )}
            </Button>
            <Button onClick={() => setShowCustomDialog(true)} className="gap-2">
              <Plus className="h-4 w-4" />
              Nuevo ejercicio
            </Button>
          </div>
        </div>

        {/* Panel de filtros */}
        {showFilters && (
          <div className="bg-muted/50 p-4 rounded-lg mb-6 space-y-4">
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
              <div>
                <Label className="mb-2 block">Tipo</Label>
                <Select
                  value={filters.type || 'all'}
                  onValueChange={(value) => dispatch(setFilters({ type: value === 'all' ? null : value }))}
                >
                  <SelectTrigger>
                    <SelectValue />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="all">Todos</SelectItem>
                    <SelectItem value="cardio">Cardio</SelectItem>
                    <SelectItem value="fuerza">Fuerza</SelectItem>
                    <SelectItem value="movilidad">Movilidad</SelectItem>
                  </SelectContent>
                </Select>
              </div>

              <div>
                <Label className="mb-2 block">Dificultad</Label>
                <Select
                  value={filters.difficulty || 'all'}
                  onValueChange={(value) => dispatch(setFilters({ difficulty: value === 'all' ? null : value }))}
                >
                  <SelectTrigger>
                    <SelectValue />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="all">Todas</SelectItem>
                    <SelectItem value="baja">Baja</SelectItem>
                    <SelectItem value="media">Media</SelectItem>
                    <SelectItem value="alta">Alta</SelectItem>
                  </SelectContent>
                </Select>
              </div>

              <div>
                <Label className="mb-2 block">Unidad</Label>
                <Select
                  value={filters.progressUnit || 'all'}
                  onValueChange={(value) => dispatch(setFilters({ progressUnit: value === 'all' ? null : value }))}
                >
                  <SelectTrigger>
                    <SelectValue />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="all">Todas</SelectItem>
                    <SelectItem value="reps">Repeticiones</SelectItem>
                    <SelectItem value="min">Minutos</SelectItem>
                    <SelectItem value="km">Kilómetros</SelectItem>
                  </SelectContent>
                </Select>
              </div>

              <div>
                <Label className="mb-2 block">Ordenar por</Label>
                <Select value={sortBy} onValueChange={(value: any) => dispatch(setSortBy(value))}>
                  <SelectTrigger>
                    <SelectValue />
                  </SelectTrigger>
                  <SelectContent>
                    <SelectItem value="relevance">Relevancia</SelectItem>
                    <SelectItem value="name">Nombre</SelectItem>
                    <SelectItem value="difficulty">Dificultad</SelectItem>
                  </SelectContent>
                </Select>
              </div>
            </div>

            <div className="flex items-center justify-between">
              <div className="flex items-center gap-2">
                <Checkbox
                  id="hasVideo"
                  checked={filters.hasVideo}
                  onCheckedChange={(checked) => dispatch(setFilters({ hasVideo: !!checked }))}
                />
                <Label htmlFor="hasVideo" className="cursor-pointer">
                  Solo con video
                </Label>
              </div>

              {hasActiveFilters && (
                <Button variant="ghost" onClick={handleClearFilters} className="gap-2">
                  <X className="h-4 w-4" />
                  Limpiar filtros
                </Button>
              )}
            </div>
          </div>
        )}

        {/* Tabs: Predefinidos / Personalizados */}
        <Tabs defaultValue="predefined" className="w-full">
          <TabsList className="mb-6">
            <TabsTrigger value="predefined">
              Predefinidos ({predefinedExercises.length})
            </TabsTrigger>
            <TabsTrigger value="custom">
              Personalizados ({customExercises.length})
            </TabsTrigger>
          </TabsList>

          <TabsContent value="predefined">
            {loading ? (
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {[...Array(6)].map((_, i) => (
                  <Skeleton key={i} className="h-80 rounded-lg" />
                ))}
              </div>
            ) : predefinedExercises.length === 0 ? (
              <div className="text-center py-16">
                <p className="text-muted-foreground mb-4">No se encontraron ejercicios predefinidos</p>
                {hasActiveFilters && (
                  <Button variant="outline" onClick={handleClearFilters}>
                    Limpiar filtros
                  </Button>
                )}
              </div>
            ) : (
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {predefinedExercises.map((exercise) => (
                  <ExerciseCard key={exercise.exerciseId} exercise={exercise} />
                ))}
              </div>
            )}
          </TabsContent>

          <TabsContent value="custom">
            {loading ? (
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {[...Array(3)].map((_, i) => (
                  <Skeleton key={i} className="h-80 rounded-lg" />
                ))}
              </div>
            ) : customExercises.length === 0 ? (
              <div className="text-center py-16">
                <p className="text-muted-foreground mb-4">
                  Aún no tienes ejercicios personalizados
                </p>
                <Button onClick={() => setShowCustomDialog(true)} className="gap-2">
                  <Plus className="h-4 w-4" />
                  Crear ejercicio personalizado
                </Button>
              </div>
            ) : (
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {customExercises.map((exercise) => (
                  <ExerciseCard key={exercise.exerciseId} exercise={exercise} />
                ))}
              </div>
            )}
          </TabsContent>
        </Tabs>
      </div>

      <CustomExerciseDialog open={showCustomDialog} onClose={() => setShowCustomDialog(false)} />
    </div>
  );
}
