import { Toaster } from "@/components/ui/toaster";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { TooltipProvider } from "@/components/ui/tooltip";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Index from "./pages/Index";
import NotFound from "./pages/NotFound";
import Register from "./pages/Register";
import Login from "@/pages/Login";
import ProtectedRoute from "@/routes/ProtectedRoute";
import AppLayout from "@/layouts/AppLayout";
import AindyChat from "./pages/AIndychat";
// Protected pages
import AppPage from "@/pages/App";
import ExerciseCatalog from "@/pages/routines/ExerciseCatalog";
import MyRoutines from "@/pages/routines/MyRoutines";
import RoutineEditor from "@/pages/routines/RoutineEditor";
import AdminDashboard from "@/pages/admin/Dashboard";
import TrainerStudents from "@/pages/trainer/Students";
import StudentProgress from "@/pages/trainer/StudentProgress";
import Recommendations from "@/pages/trainer/Recommendations";
import UserProgress from "@/pages/user/Progress";
import UserRecommendations from "@/pages/user/Recommendations";
import UserReports from "@/pages/user/Reports";
import UserHistory from "@/pages/user/History";
import PredesignedRoutines from "@/pages/routines/PredesignedRoutines";
import WorkoutSession from "@/pages/routines/WorkoutSession";
import Events from "@/pages/Events";

// Redux
import { Provider } from "react-redux";
import { store } from "@/store";

const queryClient = new QueryClient();

const App = () => (
  <QueryClientProvider client={queryClient}>
    <Provider store={store}>
      <TooltipProvider>
        <Toaster />
        <Sonner />
        <BrowserRouter basename="/borojo-front">
          <Routes>
            {/* Public routes */}
            <Route path="/" element={<Index />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            
            {/* Protected routes */}
            <Route
              path="/app"
              element={
                <ProtectedRoute>
                  <AppPage />
                </ProtectedRoute>
              }
            />
            
            {/* User routes - Routines */}
            <Route
              path="/app/user/exercises"
              element={
                <ProtectedRoute>
                  <AppLayout>
                    <ExerciseCatalog />
                  </AppLayout>
                </ProtectedRoute>
              }
            />
            <Route
              path="/app/user/routines"
              element={
                <ProtectedRoute>
                  <AppLayout>
                    <MyRoutines />
                  </AppLayout>
                </ProtectedRoute>
              }
            />
            <Route
              path="/app/user/routines/new"
              element={
                <ProtectedRoute>
                  <AppLayout>
                    <RoutineEditor />
                  </AppLayout>
                </ProtectedRoute>
              }
            />
            <Route
              path="/app/user/routines/:id/edit"
              element={
                <ProtectedRoute>
                  <AppLayout>
                    <RoutineEditor />
                  </AppLayout>
                </ProtectedRoute>
              }
            />
            <Route
              path="/app/user/routines/:workoutId/train"
              element={
                <ProtectedRoute>
                  <AppLayout>
                    <WorkoutSession />
                  </AppLayout>
                </ProtectedRoute>
              }
            />
            <Route
              path="/app/user/routines/predesigned"
              element={
                <ProtectedRoute>
                  <AppLayout>
                    <PredesignedRoutines />
                  </AppLayout>
                </ProtectedRoute>
              }
            />
            <Route
              path="/app/user/progress"
              element={
                <ProtectedRoute>
                  <AppLayout>
                    <UserProgress />
                  </AppLayout>
                </ProtectedRoute>
              }
            />
            <Route
              path="/app/user/recommendations"
              element={
                <ProtectedRoute>
                  <AppLayout>
                    <UserRecommendations />
                  </AppLayout>
                </ProtectedRoute>
              }
            />
            <Route
              path="/app/user/reports"
              element={
                <ProtectedRoute>
                  <AppLayout>
                    <UserReports />
                  </AppLayout>
                </ProtectedRoute>
              }
            />
            <Route
              path="/app/user/history"
              element={
                <ProtectedRoute>
                  <AppLayout>
                    <UserHistory />
                  </AppLayout>
                </ProtectedRoute>
              }
            />
            <Route
              path="/app/events"
              element={
                <ProtectedRoute>
                  <AppLayout>
                    <Events />
                  </AppLayout>
                </ProtectedRoute>
              }
            />
            
            {/* Trainer routes */}
            <Route
              path="/app/trainer/students"
              element={
                <ProtectedRoute>
                  <AppLayout>
                    <TrainerStudents />
                  </AppLayout>
                </ProtectedRoute>
              }
            />
            <Route
              path="/app/trainer/students/:studentEmail/progress"
              element={
                <ProtectedRoute>
                  <AppLayout>
                    <StudentProgress />
                  </AppLayout>
                </ProtectedRoute>
              }
            />
            <Route
              path="/app/trainer/students/:studentEmail/recommendations"
              element={
                <ProtectedRoute>
                  <AppLayout>
                    <Recommendations />
                  </AppLayout>
                </ProtectedRoute>
              }
            />
            
            {/* Admin routes */}
            <Route
              path="/app/admin/dashboard"
              element={
                <ProtectedRoute>
                  <AppLayout>
                    <AdminDashboard />
                  </AppLayout>
                </ProtectedRoute>
              }
            />
            {/* AINDY Chat */}
            <Route
              path="/app/aindy"
              element={
                <ProtectedRoute>
                  <AppLayout>
                    <AindyChat />
                  </AppLayout>
                </ProtectedRoute>
              }
            />
            
            {/* ADD ALL CUSTOM ROUTES ABOVE THE CATCH-ALL "*" ROUTE */}
            <Route path="*" element={<NotFound />} />
          </Routes>
        </BrowserRouter>
      </TooltipProvider>
    </Provider>
  </QueryClientProvider>
);

export default App;