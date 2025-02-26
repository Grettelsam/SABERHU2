<?php
include 'conexion.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $usuario = $_POST['usuario'];
    $fecha = $_POST['fecha'];
    $hora_entrada = $_POST['hora_entrada'];
    $hora_salida = $_POST['hora_salida'];

    $sql = "INSERT INTO horario (usuario, fecha, hora_entrada, hora_salida) VALUES (?, ?, ?, ?)";
    $stmt = $pdo->prepare($sql);
    $stmt->execute([$usuario, $fecha, $hora_entrada, $hora_salida]);
}
?>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro de Horarios</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f8ff;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background-color: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 255, 0.2);
            width: 100%;
            max-width: 400px;
        }
        h2 {
            text-align: center;
            color: #007BFF;
        }
        form {
            display: flex;
            flex-direction: column;
        }
        input, button {
            margin-bottom: 10px;
            padding: 10px;
            border: 1px solid #007BFF;
            border-radius: 5px;
        }
        button {
            background-color: #007BFF;
            color: white;
            font-size: 16px;
            cursor: pointer;
            border: none;
        }
        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Registrar Horario</h2>
        <form method="POST">
            <label for="usuario">Usuario</label>
            <input type="text" name="usuario" required>
            <input type="date" name="fecha" required>
            <input type="time" name="hora_entrada" required>
            <input type="time" name="hora_salida" required>
            <button type="submit">Registrar</button>
            <a href="index.html">Volver</a>
        </form>
    </div>
</body>
</html>