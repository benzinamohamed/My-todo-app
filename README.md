# My To-Do App

**My To-Do App** is a simple Android application designed to help users manage their tasks efficiently. This app allows users to add, edit, delete, and mark tasks as complete. The tasks are stored in a local SQLite database, ensuring that users can access their tasks even when offline.

## Features

- **Add New Tasks**: Users can add new tasks using a floating action button, which opens a bottom sheet dialog where they can input their task details.
- **Edit Existing Tasks**: Users can edit the details of their existing tasks.
- **Delete Tasks**: Users can delete tasks using the delete button associated with each task.
- **Mark Tasks as Completed**: Users can mark tasks as completed by checking a checkbox. The status of each task is stored in the database.
- **Persistent Storage**: All tasks are stored locally in an SQLite database, ensuring data persistence across app sessions.

## Project Structure

The project is organized into the following main components:

- **MainActivity**: The main activity that initializes the user interface and handles user interactions such as adding tasks.
- **TaskCL**: A model class representing a task object, including attributes such as task name, ID, and status.
- **recyclerviewAdapter**: A RecyclerView adapter that binds the list of tasks to the RecyclerView in the user interface. It handles actions like editing, deleting, and marking tasks as completed.
- **Databasehandler**: A helper class that manages the SQLite database operations such as creating tables, inserting tasks, retrieving tasks, updating tasks, and deleting tasks.

## Screenshots

<table>
  <tr>
    <td>
      <img src="https://github.com/benzinamohamed/My-todo-app/blob/main/photo_5954117754221084756_y.jpg" alt="Add Task" width="300"/>
      <p style="text-align: center;"</p>
    </td>
    <td>
      <img src="https://github.com/benzinamohamed/My-todo-app/blob/main/photo_5954117754221084757_y.jpg" alt="Edit Task" width="300"/>
      <p style="text-align: center;"></p>
    </td>
  </tr>
  <tr>
    <td>
      <img src="https://github.com/benzinamohamed/My-todo-app/blob/main/photo_5954117754221084758_y.jpg" alt="Delete Task" width="300"/>
      <p style="text-align: center;"></p>
    </td>
    <td>
      <img src="https://github.com/benzinamohamed/My-todo-app/blob/main/photo_5954117754221084759_y.jpg" alt="Completed Task" width="300"/>
      <p style="text-align: center;"></p>
    </td>
  </tr>
</table>
