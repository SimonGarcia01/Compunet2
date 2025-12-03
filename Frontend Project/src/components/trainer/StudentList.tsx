import { useState } from "react";
import { Input } from "@/components/ui/input";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Search } from "lucide-react";
import { StudentCard } from "./StudentCard";
import type { Student } from "@/store/trainer/trainerSlice";

interface StudentListProps {
  students: Student[];
  onViewProgress: (student: Student) => void;
}

export function StudentList({ students, onViewProgress }: StudentListProps) {
  const [searchTerm, setSearchTerm] = useState("");
  const [statusFilter, setStatusFilter] = useState<"all" | "active" | "inactive">("all");
  const [sortBy, setSortBy] = useState<"name" | "date">("name");

  const filteredStudents = students
    .filter((student) => {
      const matchesSearch = 
        student.name?.toLowerCase().includes(searchTerm.toLowerCase()) ||
        student.email.toLowerCase().includes(searchTerm.toLowerCase());
      
      const matchesStatus = 
        statusFilter === "all" ||
        (statusFilter === "active" && student.isActive) ||
        (statusFilter === "inactive" && !student.isActive);
      
      return matchesSearch && matchesStatus;
    })
    .sort((a, b) => {
      if (sortBy === "name") {
        const nameA = a.name || a.email;
        const nameB = b.name || b.email;
        return nameA.localeCompare(nameB);
      } else {
        return new Date(b.startDate).getTime() - new Date(a.startDate).getTime();
      }
    });

  return (
    <div className="space-y-6">
      <div className="flex flex-col sm:flex-row gap-4">
        <div className="relative flex-1">
          <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-4 w-4 text-muted-foreground" />
          <Input
            placeholder="Buscar por nombre o email..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="pl-10"
          />
        </div>
        <Select value={statusFilter} onValueChange={(value: any) => setStatusFilter(value)}>
          <SelectTrigger className="w-full sm:w-[180px]">
            <SelectValue placeholder="Estado" />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="all">Todos</SelectItem>
            <SelectItem value="active">Activos</SelectItem>
            <SelectItem value="inactive">Inactivos</SelectItem>
          </SelectContent>
        </Select>
        <Select value={sortBy} onValueChange={(value: any) => setSortBy(value)}>
          <SelectTrigger className="w-full sm:w-[180px]">
            <SelectValue placeholder="Ordenar por" />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="name">Nombre</SelectItem>
            <SelectItem value="date">Fecha de inicio</SelectItem>
          </SelectContent>
        </Select>
      </div>

      {filteredStudents.length === 0 ? (
        <div className="text-center py-12 text-muted-foreground">
          <p>No se encontraron estudiantes</p>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {filteredStudents.map((student) => (
            <StudentCard
              key={student.id}
              student={student}
              onViewProgress={onViewProgress}
            />
          ))}
        </div>
      )}
    </div>
  );
}
