import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { AppDispatch } from "@/store/index";
import type { RootState } from "@/store/index";
import { fetchMyStudents } from "@/store/trainer/thunk";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { StudentList } from "@/components/trainer/StudentList";
import { AddStudentDialog } from "@/components/trainer/AddStudentDialog";
import { Loader2, UserPlus } from "lucide-react";
import type { Student } from "@/store/trainer/trainerSlice";

export default function TrainerStudents() {
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  const { students, loading, error } = useSelector((state: RootState) => state.trainer);
  const [dialogOpen, setDialogOpen] = useState(false);

  useEffect(() => {
    dispatch(fetchMyStudents());
  }, [dispatch]);

  const handleViewProgress = (student: Student) => {
    navigate(`/app/trainer/students/${encodeURIComponent(student.email)}/progress`);
  };

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold mb-2">Mis Alumnos</h1>
          <p className="text-muted-foreground">
            Gestiona y supervisa a tus alumnos
          </p>
        </div>
        <Button onClick={() => setDialogOpen(true)}>
          <UserPlus className="mr-2 h-4 w-4" />
          Agregar Estudiante
        </Button>
      </div>

      {loading && students.length === 0 ? (
        <Card>
          <CardContent className="flex items-center justify-center py-12">
            <Loader2 className="h-6 w-6 animate-spin text-muted-foreground" />
            <span className="ml-2 text-muted-foreground">Cargando estudiantes...</span>
          </CardContent>
        </Card>
      ) : error ? (
        <Card>
          <CardContent className="py-12">
            <p className="text-center text-destructive">
              {error}
            </p>
            <div className="mt-4 flex justify-center">
              <Button variant="outline" onClick={() => dispatch(fetchMyStudents())}>
                Reintentar
              </Button>
            </div>
          </CardContent>
        </Card>
      ) : students.length === 0 ? (
        <Card>
          <CardHeader>
            <CardTitle>Lista de Alumnos</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="text-center py-12">
              <p className="text-muted-foreground mb-4">
                No tienes alumnos asignados aún.
              </p>
              <Button onClick={() => setDialogOpen(true)}>
                <UserPlus className="mr-2 h-4 w-4" />
                Agregar tu primer estudiante
              </Button>
            </div>
          </CardContent>
        </Card>
      ) : (
        <Card>
          <CardHeader>
            <CardTitle>Lista de Alumnos ({students.length})</CardTitle>
          </CardHeader>
          <CardContent>
            <StudentList students={students} onViewProgress={handleViewProgress} />
          </CardContent>
        </Card>
      )}

      <AddStudentDialog open={dialogOpen} onClose={() => setDialogOpen(false)} />
    </div>
  );
}

