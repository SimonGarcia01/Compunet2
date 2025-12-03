import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import { Eye, Calendar, Mail } from "lucide-react";
import { format } from "date-fns";
import { es } from "date-fns/locale";
import type { Student } from "@/store/trainer/trainerSlice";

interface StudentCardProps {
  student: Student;
  onViewProgress: (student: Student) => void;
}

export function StudentCard({ student, onViewProgress }: StudentCardProps) {
  const initials = student.name
    ? student.name.split(' ').map(n => n[0]).join('').toUpperCase().slice(0, 2)
    : student.email.slice(0, 2).toUpperCase();

  return (
    <Card className="hover:shadow-lg transition-shadow">
      <CardHeader className="pb-3">
        <div className="flex items-start justify-between">
          <div className="flex items-center gap-3">
            <Avatar className="h-12 w-12">
              <AvatarImage src={student.photoUrl} alt={student.name || student.email} />
              <AvatarFallback>{initials}</AvatarFallback>
            </Avatar>
            <div>
              <CardTitle className="text-lg">{student.name || student.email}</CardTitle>
              <div className="flex items-center gap-1 text-sm text-muted-foreground mt-1">
                <Mail className="h-3 w-3" />
                <span>{student.email}</span>
              </div>
            </div>
          </div>
          <Badge variant={student.isActive ? "default" : "secondary"}>
            {student.isActive ? "Activo" : "Inactivo"}
          </Badge>
        </div>
      </CardHeader>
      <CardContent>
        <div className="flex items-center gap-2 text-sm text-muted-foreground mb-4">
          <Calendar className="h-4 w-4" />
          <span>
            Inicio: {format(new Date(student.startDate), "d 'de' MMMM, yyyy", { locale: es })}
          </span>
        </div>
        <Button 
          onClick={() => onViewProgress(student)}
          className="w-full"
          size="sm"
        >
          <Eye className="h-4 w-4 mr-2" />
          Ver Progreso
        </Button>
      </CardContent>
    </Card>
  );
}
