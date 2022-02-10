package com.example.thebeast.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.thebeast.daos.UserDao;
import com.example.thebeast.daos.WorkoutDao;
import com.example.thebeast.entitys.User;
import com.example.thebeast.entitys.Workout;
import com.example.thebeast.roomdatabase.TheBeastDataBase;

import java.util.List;

public class WorkoutRepository {

    private WorkoutDao workoutDao;
    private LiveData<List<Workout>> allWorkouts;

    public WorkoutRepository(Application application){
        TheBeastDataBase database = TheBeastDataBase.getInstance(application);

        workoutDao = database.workoutDao();
        allWorkouts = workoutDao.getAllWorkouts();
    }

    public void insert(Workout workout){

        new WorkoutRepository.InsertWorkoutAsyncTask(workoutDao).execute(workout);
    }

    public void update(Workout workout){

        new WorkoutRepository.UpdateWorkoutAsyncTask(workoutDao).execute(workout);
    }

    public void delete(Workout workout){

        new WorkoutRepository.DeleteWorkoutAsyncTask(workoutDao).execute(workout);
    }

    public LiveData<List<Workout>> getAllWorkouts(){
        return allWorkouts;
    }



    //Asynctasks
    private static class InsertWorkoutAsyncTask extends AsyncTask<Workout, Void, Void> {

        private WorkoutDao workoutDao;

        private InsertWorkoutAsyncTask (WorkoutDao workoutDao){
            this.workoutDao=workoutDao;
        }

        @Override
        protected Void doInBackground(Workout... workouts) {
            workoutDao.insertWorkout(workouts[0]);
            return null;
        }
    }

    private static class UpdateWorkoutAsyncTask extends AsyncTask<Workout, Void, Void>{

        private WorkoutDao workoutDao;

        private UpdateWorkoutAsyncTask (WorkoutDao workoutDao){
            this.workoutDao=workoutDao;
        }

        @Override
        protected Void doInBackground(Workout... workouts) {
            workoutDao.updateWorkout(workouts[0]);
            return null;
        }
    }

    private static class DeleteWorkoutAsyncTask extends AsyncTask<Workout, Void, Void>{

        private WorkoutDao workoutDao;

        private DeleteWorkoutAsyncTask (WorkoutDao workoutDao){
            this.workoutDao=workoutDao;
        }

        @Override
        protected Void doInBackground(Workout... workouts) {
            workoutDao.deleteWorkout(workouts[0]);
            return null;
        }
    }
}
