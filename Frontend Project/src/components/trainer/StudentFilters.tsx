import { useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Slider } from "@/components/ui/slider";
import { Calendar } from "@/components/ui/calendar";
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover";
import { CalendarIcon, X } from "lucide-react";
import { format } from "date-fns";
import { es } from "date-fns/locale";
import type { ProgressFilters, WorkoutProgram } from "@/store/trainer/trainerSlice";

interface StudentFiltersProps {
  filters: ProgressFilters;
  workouts: WorkoutProgram[];
  onApplyFilters: (filters: ProgressFilters) => void;
  onClearFilters: () => void;
}

export function StudentFilters({ 
  filters, 
  workouts,
  onApplyFilters, 
  onClearFilters 
}: StudentFiltersProps) {
  const [localFilters, setLocalFilters] = useState<ProgressFilters>(filters);
  const [startDate, setStartDate] = useState<Date | undefined>(
    filters.startDate ? new Date(filters.startDate) : undefined
  );
  const [endDate, setEndDate] = useState<Date | undefined>(
    filters.endDate ? new Date(filters.endDate) : undefined
  );

  const handleApply = () => {
    const appliedFilters = {
      ...localFilters,
      startDate: startDate ? format(startDate, 'yyyy-MM-dd') : undefined,
      endDate: endDate ? format(endDate, 'yyyy-MM-dd') : undefined,
    };
    onApplyFilters(appliedFilters);
  };

  const handleClear = () => {
    setLocalFilters({});
    setStartDate(undefined);
    setEndDate(undefined);
    onClearFilters();
  };

  return (
    <Card>
      <CardHeader>
        <CardTitle className="text-lg">Filtros</CardTitle>
      </CardHeader>
      <CardContent className="space-y-4">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          {/* Date Range */}
          <div className="space-y-2">
            <Label>Fecha de inicio</Label>
            <Popover>
              <PopoverTrigger asChild>
                <Button
                  variant="outline"
                  className="w-full justify-start text-left font-normal"
                >
                  <CalendarIcon className="mr-2 h-4 w-4" />
                  {startDate ? format(startDate, "PPP", { locale: es }) : "Seleccionar"}
                </Button>
              </PopoverTrigger>
              <PopoverContent className="w-auto p-0">
                <Calendar
                  mode="single"
                  selected={startDate}
                  onSelect={setStartDate}
                  locale={es}
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
                  className="w-full justify-start text-left font-normal"
                >
                  <CalendarIcon className="mr-2 h-4 w-4" />
                  {endDate ? format(endDate, "PPP", { locale: es }) : "Seleccionar"}
                </Button>
              </PopoverTrigger>
              <PopoverContent className="w-auto p-0">
                <Calendar
                  mode="single"
                  selected={endDate}
                  onSelect={setEndDate}
                  locale={es}
                  disabled={(date) => startDate ? date < startDate : false}
                />
              </PopoverContent>
            </Popover>
          </div>

          {/* Workout Filter */}
          <div className="space-y-2">
            <Label>Rutina</Label>
            <Select
              value={localFilters.workoutId || "all"}
              onValueChange={(value) =>
                setLocalFilters({ ...localFilters, workoutId: value === "all" ? undefined : value })
              }
            >
              <SelectTrigger>
                <SelectValue placeholder="Todas las rutinas" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="all">Todas las rutinas</SelectItem>
                {workouts.map((workout) => (
                  <SelectItem key={workout.workoutId} value={workout.workoutId}>
                    {workout.name}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
          </div>

          {/* Period Type */}
          <div className="space-y-2">
            <Label>Tipo de período</Label>
            <Select
              value={localFilters.periodType || "all"}
              onValueChange={(value) =>
                setLocalFilters({ ...localFilters, periodType: value === "all" ? undefined : value as any })
              }
            >
              <SelectTrigger>
                <SelectValue placeholder="Todos" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="all">Todos</SelectItem>
                <SelectItem value="DAILY">Diario</SelectItem>
                <SelectItem value="WEEKLY">Semanal</SelectItem>
              </SelectContent>
            </Select>
          </div>

          {/* RPE Range */}
          <div className="space-y-2 md:col-span-2">
            <Label>Rango de RPE: {localFilters.minRpe || 0} - {localFilters.maxRpe || 10}</Label>
            <div className="flex gap-4 items-center">
              <Slider
                value={[localFilters.minRpe || 0]}
                onValueChange={([value]) => setLocalFilters({ ...localFilters, minRpe: value })}
                max={10}
                step={1}
                className="flex-1"
              />
              <Slider
                value={[localFilters.maxRpe || 10]}
                onValueChange={([value]) => setLocalFilters({ ...localFilters, maxRpe: value })}
                max={10}
                step={1}
                className="flex-1"
              />
            </div>
          </div>

          {/* Min Calories */}
          <div className="space-y-2">
            <Label>Calorías mínimas</Label>
            <Input
              type="number"
              placeholder="0"
              value={localFilters.minCalories || ''}
              onChange={(e) =>
                setLocalFilters({ ...localFilters, minCalories: Number(e.target.value) || undefined })
              }
            />
          </div>
        </div>

        <div className="flex gap-2 pt-4">
          <Button onClick={handleApply} className="flex-1">
            Aplicar Filtros
          </Button>
          <Button onClick={handleClear} variant="outline">
            <X className="h-4 w-4 mr-2" />
            Limpiar
          </Button>
        </div>
      </CardContent>
    </Card>
  );
}
