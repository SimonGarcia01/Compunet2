import { Search, Filter, X } from "lucide-react";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Label } from "@/components/ui/label";
import { Sheet, SheetContent, SheetHeader, SheetTitle, SheetTrigger } from "@/components/ui/sheet";
import type { EventFilters as Filters } from "@/store/events/eventsSlice";

interface EventFiltersProps {
  filters: Filters;
  onFiltersChange: (filters: Filters) => void;
}

export function EventFilters({ filters, onFiltersChange }: EventFiltersProps) {
  const hasActiveFilters = filters.type || filters.status || filters.dateFrom || filters.dateTo;

  const clearFilters = () => {
    onFiltersChange({});
  };

  return (
    <div className="space-y-4">
      {/* Search */}
      <div className="relative">
        <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground" />
        <Input
          placeholder="Buscar eventos..."
          value={filters.search || ""}
          onChange={(e) => onFiltersChange({ ...filters, search: e.target.value })}
          className="pl-9"
        />
      </div>

      {/* Mobile Filters */}
      <div className="flex gap-2 md:hidden">
        <Sheet>
          <SheetTrigger asChild>
            <Button variant="outline" className="flex-1">
              <Filter className="h-4 w-4 mr-2" />
              Filtros
              {hasActiveFilters && <span className="ml-2 bg-primary text-primary-foreground rounded-full w-5 h-5 text-xs flex items-center justify-center">!</span>}
            </Button>
          </SheetTrigger>
          <SheetContent>
            <SheetHeader>
              <SheetTitle>Filtros</SheetTitle>
            </SheetHeader>
            <div className="space-y-4 mt-4">
              <FilterContent filters={filters} onFiltersChange={onFiltersChange} />
              {hasActiveFilters && (
                <Button variant="outline" onClick={clearFilters} className="w-full">
                  <X className="h-4 w-4 mr-2" />
                  Limpiar filtros
                </Button>
              )}
            </div>
          </SheetContent>
        </Sheet>
      </div>

      {/* Desktop Filters */}
      <div className="hidden md:block space-y-4">
        <div className="flex items-center justify-between">
          <h3 className="font-medium">Filtros</h3>
          {hasActiveFilters && (
            <Button variant="ghost" size="sm" onClick={clearFilters}>
              <X className="h-4 w-4 mr-2" />
              Limpiar
            </Button>
          )}
        </div>
        <FilterContent filters={filters} onFiltersChange={onFiltersChange} />
      </div>
    </div>
  );
}

function FilterContent({ filters, onFiltersChange }: EventFiltersProps) {
  return (
    <>
      <div className="space-y-2">
        <Label>Tipo de Evento</Label>
        <Select
          value={filters.type || "all"}
          onValueChange={(value) => onFiltersChange({ ...filters, type: value === "all" ? undefined : value as any })}
        >
          <SelectTrigger>
            <SelectValue />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="all">Todos</SelectItem>
            <SelectItem value="class">Clases</SelectItem>
            <SelectItem value="tournament">Torneos</SelectItem>
            <SelectItem value="schedule">Horarios</SelectItem>
            <SelectItem value="social">Eventos Sociales</SelectItem>
            <SelectItem value="open_call">Convocatorias</SelectItem>
          </SelectContent>
        </Select>
      </div>

      <div className="space-y-2">
        <Label>Estado</Label>
        <Select
          value={filters.status || "all"}
          onValueChange={(value) => onFiltersChange({ ...filters, status: value === "all" ? undefined : value as any })}
        >
          <SelectTrigger>
            <SelectValue />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="all">Todos</SelectItem>
            <SelectItem value="upcoming">Próximos</SelectItem>
            <SelectItem value="ongoing">En Curso</SelectItem>
            <SelectItem value="completed">Completados</SelectItem>
            <SelectItem value="cancelled">Cancelados</SelectItem>
          </SelectContent>
        </Select>
      </div>

      <div className="space-y-2">
        <Label>Desde</Label>
        <Input
          type="date"
          value={filters.dateFrom || ""}
          onChange={(e) => onFiltersChange({ ...filters, dateFrom: e.target.value })}
        />
      </div>

      <div className="space-y-2">
        <Label>Hasta</Label>
        <Input
          type="date"
          value={filters.dateTo || ""}
          onChange={(e) => onFiltersChange({ ...filters, dateTo: e.target.value })}
        />
      </div>
    </>
  );
}
