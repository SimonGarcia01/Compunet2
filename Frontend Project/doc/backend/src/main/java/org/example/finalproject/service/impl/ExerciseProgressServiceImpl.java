package org.example.finalproject.service.impl;

import jakarta.transaction.Transactional;
import org.example.finalproject.api.v1.dtos.ExerciseProgressRequest;
import org.example.finalproject.api.v1.dtos.ExerciseProgressResponse;
import org.example.finalproject.api.v1.mappers.ExerciseProgressMapper;
import org.example.finalproject.entity.Exercise;
import org.example.finalproject.entity.ExerciseProgress;
import org.example.finalproject.entity.User;
import org.example.finalproject.entity.WorkoutProgram;
import org.example.finalproject.exceptions.MissingInfoException;
import org.example.finalproject.exceptions.ResourceNotFoundException;
import org.example.finalproject.entity.TrainerTrainee;
import org.example.finalproject.repository.ExerciseProgressRepository;
import org.example.finalproject.repository.ExerciseRepository;
import org.example.finalproject.repository.TrainerTraineeRepository;
import org.example.finalproject.repository.UserRepository;
import org.example.finalproject.repository.WorkoutProgramRepository;
import org.example.finalproject.service.ExerciseProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseProgressServiceImpl implements ExerciseProgressService {

    // Beans
    @Autowired
    private ExerciseProgressRepository exerciseProgressRepository;
    @Autowired
    private ExerciseProgressMapper exerciseProgressMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkoutProgramRepository workoutProgramRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private TrainerTraineeRepository trainerTraineeRepository;

    // Get my progress (progress of authenticated user)
    @Override
    public List<ExerciseProgressResponse> getMyProgress() {
        // Extraer el email del usuario del SecurityContext (token JWT)
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        
        // Buscar todos los registros de progreso del usuario autenticado
        List<ExerciseProgress> progressList = exerciseProgressRepository.findByUser_Email(userEmail);
        
        return progressList.stream()
                .map(exerciseProgressMapper::toExerciseProgressResponse)
                .toList();
    }

    // Get student progress (for trainers to view their students' progress)
    @Override
    public List<ExerciseProgressResponse> getStudentProgress(String studentEmail) {
        // Extraer el email del entrenador del SecurityContext (token JWT)
        String trainerEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        
        // Verificar que el estudiante existe
        User trainee = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with email: " + studentEmail));
        
        // Verificar que existe una relación TrainerTrainee entre el entrenador y el estudiante
        // Buscar todas las relaciones del entrenador y verificar si alguna tiene este estudiante
        boolean isAssigned = trainerTraineeRepository.findAll().stream()
                .anyMatch(tt -> {
                    User ttTrainer = tt.getTrainer();
                    User ttTrainee = tt.getTrainee();
                    return ttTrainer != null && ttTrainee != null
                            && ttTrainer.getEmail() != null && ttTrainee.getEmail() != null
                            && ttTrainer.getEmail().equals(trainerEmail) 
                            && ttTrainee.getEmail().equals(studentEmail);
                });
        
        if (!isAssigned) {
            throw new ResourceNotFoundException("Student not assigned to this trainer. Please verify the student is linked to your trainer account.");
        }
        
        // Buscar todos los registros de progreso del estudiante
        // Si no hay registros, retornar lista vacía (no es un error)
        List<ExerciseProgress> progressList = exerciseProgressRepository.findByUser_Email(studentEmail);
        
        return progressList.stream()
                .map(exerciseProgressMapper::toExerciseProgressResponse)
                .toList();
    }

    // Find exercise progress by ID
    @Override
    public ExerciseProgressResponse findById(Integer id) {
        return exerciseProgressRepository.findById(id)
                .map(exerciseProgressMapper::toExerciseProgressResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise Progress not found with id: " + id));
    }

    // Get all exercise progress
    @Override
    public List<ExerciseProgressResponse> getAllExerciseProgress() {
        return exerciseProgressRepository.findAll().stream()
                .map(exerciseProgressMapper::toExerciseProgressResponse)
                .toList();
    }

    // Create a new exercise progress
    @Override
    @Transactional
    public void createExerciseProgress(ExerciseProgressRequest request) {
        if (request.getUser() == null ||
                request.getUser().getEmail() == null ||
                request.getRecordDate() == null ||
                request.getPeriodType() == null) {
            throw new MissingInfoException("One or more fields were not filled. Try again.");
        }

        // Extraer el email del usuario del SecurityContext (token JWT)
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        
        // Buscar el usuario existente por email
        User existingUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userEmail));

        // Crear el registro de progreso
        ExerciseProgress newProgress = new ExerciseProgress();
        newProgress.setUser(existingUser);
        newProgress.setRecordDate(request.getRecordDate());
        newProgress.setPeriodType(request.getPeriodType());
        newProgress.setRepetitions(request.getRepetitions());
        newProgress.setTimeMinutes(request.getTimeMinutes());
        newProgress.setDistanceKm(request.getDistanceKm());
        newProgress.setRpe(request.getRpe());
        newProgress.setNotes(request.getNotes());
        newProgress.setEstimatedCaloriesBurnt(request.getEstimatedCaloriesBurnt());

        // Si se proporciona workoutId, buscar y asignar el WorkoutProgram
        if (request.getWorkoutId() != null) {
            WorkoutProgram workoutProgram = workoutProgramRepository.findById(request.getWorkoutId())
                    .orElseThrow(() -> new ResourceNotFoundException("Workout Program not found with id: " + request.getWorkoutId()));
            newProgress.setWorkoutProgram(workoutProgram);
        }

        // Si se proporciona exerciseId, buscar y asignar el Exercise
        if (request.getExerciseId() != null) {
            Exercise exercise = exerciseRepository.findById(request.getExerciseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Exercise not found with id: " + request.getExerciseId()));
            newProgress.setExercise(exercise);
        }

        exerciseProgressRepository.save(newProgress);
    }

    // Update an existing exercise progress
    @Override
    @Transactional
    public void updateExerciseProgress(Integer id, ExerciseProgressRequest request) {
        ExerciseProgress progress = exerciseProgressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise Progress not found with id: " + id));

        // Verificar que el usuario autenticado es el dueño del registro
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!progress.getUser().getEmail().equals(userEmail)) {
            throw new ResourceNotFoundException("You don't have permission to update this progress record");
        }

        if (request.getRecordDate() != null) {
            progress.setRecordDate(request.getRecordDate());
        }
        if (request.getPeriodType() != null) {
            progress.setPeriodType(request.getPeriodType());
        }
        if (request.getRepetitions() != null) {
            progress.setRepetitions(request.getRepetitions());
        }
        if (request.getTimeMinutes() != null) {
            progress.setTimeMinutes(request.getTimeMinutes());
        }
        if (request.getDistanceKm() != null) {
            progress.setDistanceKm(request.getDistanceKm());
        }
        if (request.getRpe() != null) {
            progress.setRpe(request.getRpe());
        }
        if (request.getNotes() != null) {
            progress.setNotes(request.getNotes());
        }
        if (request.getEstimatedCaloriesBurnt() != null) {
            progress.setEstimatedCaloriesBurnt(request.getEstimatedCaloriesBurnt());
        }

        // Update workout program if provided
        if (request.getWorkoutId() != null) {
            WorkoutProgram workoutProgram = workoutProgramRepository.findById(request.getWorkoutId())
                    .orElseThrow(() -> new ResourceNotFoundException("Workout Program not found with id: " + request.getWorkoutId()));
            progress.setWorkoutProgram(workoutProgram);
        }

        // Update exercise if provided
        if (request.getExerciseId() != null) {
            Exercise exercise = exerciseRepository.findById(request.getExerciseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Exercise not found with id: " + request.getExerciseId()));
            progress.setExercise(exercise);
        }

        exerciseProgressRepository.save(progress);
    }

    // Delete an exercise progress
    @Override
    @Transactional
    public void deleteExerciseProgress(Integer id) {
        ExerciseProgress progress = exerciseProgressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise Progress not found with id: " + id));

        // Verificar que el usuario autenticado es el dueño del registro
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!progress.getUser().getEmail().equals(userEmail)) {
            throw new ResourceNotFoundException("You don't have permission to delete this progress record");
        }

        exerciseProgressRepository.deleteById(id);
    }
}

