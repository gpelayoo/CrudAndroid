# Proyecto Kotlin con Firestore para Gestión de Productos (CRUD BASICO)

Este proyecto tiene como objetivo demostrar el uso de Kotlin junto con Firestore para realizar operaciones (CRUD) básicas de gestión de productos.

## Requisitos

- Android Studio Jellyfish | 2023.3.1 Patch 2 o superior.
- Android Gradle Plugin Version 8.4.2
- Gradle Version 8.6
- Kotlin 1.9.22 o superior.

## Tecnologías utilizadas:

- Kotlin: Lenguaje de programación principal para el desarrollo del proyecto.
- Firestore: Base de datos NoSQL de Firebase utilizada para almacenar y sincronizar los datos de productos.

## Estructura del proyecto

```lua
|-- 📂 app
|   |-- 📂 src
|       |-- 📂 main
|           |-- 📂 java/pe/pcs/firestorebasicoyt/
|		|-- 📂 data
|		    |-- 📂 common
|               	|-- 📄 FirestoreCnnstants.kt
|               	|-- 📄 FirestoreInstance.kt
|		    |-- 📂 model
|               	|-- 📄 ProductModel.kt
|		    |-- 📂 repository
|               	|-- 📄 ProductRepository.kt
|		|-- 📂 presentation
|		    |-- 📂 adapter
|               	|-- 📄 ProductAdapter.kt
|		    |-- 📂 common
|               	|-- 📄 MakeCall.kt
|               	|-- 📄 UiState.kt
|		    |-- 📂 ui
|               	|-- 📄 MainActivity.kt
|               	|-- 📄 OperacionProductoActivity.kt
|		|-- 📄 FirestoreBasicoYtApp.kt
|-- 📄 README.md
|-- 📄 build.gradle
```

## Pantallazos de la app

![Image text](https://github.com/programadorescs/FirestoreBasicoYT/blob/master/app/src/main/assets/FirestoreBasicoYT_001.png)
![Image text](https://github.com/programadorescs/FirestoreBasicoYT/blob/master/app/src/main/assets/FirestoreBasicoYT_002.png)

## Video en YouTube
[![Alt text](https://img.youtube.com/vi/0sIXpWT0-Ow/0.jpg)](https://www.youtube.com/watch?v=0sIXpWT0-Ow)



