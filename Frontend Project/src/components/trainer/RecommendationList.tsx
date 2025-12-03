import { useState } from "react";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Search, SortAsc, SortDesc, X } from "lucide-react";
import { RecommendationCard } from "./RecommendationCard";
import type { Recommendation } from "@/store/recommendations/recommendationsSlice";

interface RecommendationListProps {
  recommendations: Recommendation[];
  studentEmail: string;
  onEdit: (recommendation: Recommendation) => void;
  onDelete: (id: number) => void;
}

export function RecommendationList({
  recommendations,
  studentEmail,
  onEdit,
  onDelete
}: RecommendationListProps) {
  const [searchTerm, setSearchTerm] = useState("");
  const [sortOrder, setSortOrder] = useState<"desc" | "asc">("desc");

  // Filter by search term
  const filteredRecommendations = recommendations.filter((rec) =>
    rec.content.toLowerCase().includes(searchTerm.toLowerCase())
  );

  // Sort by date
  const sortedRecommendations = [...filteredRecommendations].sort((a, b) => {
    const dateA = new Date(a.commentDate).getTime();
    const dateB = new Date(b.commentDate).getTime();
    return sortOrder === "desc" ? dateB - dateA : dateA - dateB;
  });

  return (
    <div className="space-y-6">
      {/* Filters and Search */}
      <div className="flex flex-col sm:flex-row gap-4">
        <div className="relative flex-1">
          <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-4 w-4 text-muted-foreground" />
          <Input
            placeholder="Buscar en recomendaciones..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="pl-9 pr-9"
          />
          {searchTerm && (
            <Button
              variant="ghost"
              size="icon"
              className="absolute right-1 top-1/2 transform -translate-y-1/2 h-7 w-7"
              onClick={() => setSearchTerm("")}
            >
              <X className="h-4 w-4" />
            </Button>
          )}
        </div>

        <Select value={sortOrder} onValueChange={(value: "desc" | "asc") => setSortOrder(value)}>
          <SelectTrigger className="w-full sm:w-[200px]">
            <SelectValue />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="desc">
              <div className="flex items-center gap-2">
                <SortDesc className="h-4 w-4" />
                <span>Más reciente</span>
              </div>
            </SelectItem>
            <SelectItem value="asc">
              <div className="flex items-center gap-2">
                <SortAsc className="h-4 w-4" />
                <span>Más antiguo</span>
              </div>
            </SelectItem>
          </SelectContent>
        </Select>
      </div>

      {/* Results info */}
      <div className="flex items-center justify-between text-sm text-muted-foreground">
        <span>
          {sortedRecommendations.length} {sortedRecommendations.length === 1 ? "recomendación" : "recomendaciones"}
        </span>
        {searchTerm && (
          <span>Filtrando por: "{searchTerm}"</span>
        )}
      </div>

      {/* List */}
      {sortedRecommendations.length === 0 ? (
        <div className="text-center py-12">
          <p className="text-muted-foreground">
            {searchTerm
              ? "No se encontraron recomendaciones que coincidan con tu búsqueda"
              : "No hay recomendaciones aún"}
          </p>
          {searchTerm && (
            <Button
              variant="link"
              onClick={() => setSearchTerm("")}
              className="mt-2"
            >
              Limpiar búsqueda
            </Button>
          )}
        </div>
      ) : (
        <div className="grid gap-4">
          {sortedRecommendations.map((recommendation) => (
            <RecommendationCard
              key={recommendation.recommendationId}
              recommendation={recommendation}
              onEdit={() => onEdit(recommendation)}
              onDelete={() => onDelete(recommendation.recommendationId)}
            />
          ))}
        </div>
      )}
    </div>
  );
}
