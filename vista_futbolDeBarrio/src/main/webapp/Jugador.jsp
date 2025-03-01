<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="Css/Estilo.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
	rel="stylesheet">

<title>FUTBOL DE BARRIO</title>
</head>
<body>
	<header class="fixed-top">
		<!-- Contenedor principal de -->
		<div class="container-fluid ">
			<div class="row ">

				<!-- columna logo -->
				<div class="col-sm-1 col-md-1 logo"
					style="background-color: white; border-top: 2px solid black; border-left: 1px solid black">
					<img src="Imagenes/LOGOWEB.PNG"></img>
				</div>

				<!-- Columna derecha que se divide en 2 filas -->
				<div class="col-sm-11 col-md-11">
					<div class="row ">
						<!-- fila arriba -->
						<div class="col-sm-12 col-md-12" style="border: solid 2px black">
							<div class="row">
								<div class="col-sm-3 col-md-3 cabeceraArriba p-2 "></div>
								<div class="col-sm-3 col-md-3 cabeceraArriba "
									style="justify-content: right;">
									<svg xmlns="http://www.w3.org/2000/svg" width="1.3vw"
										height="1.3vw" fill="currentColor"
										style="margin-right: 4px; color: white;"
										class="bi bi-envelope-at-fill" viewBox="0 0 16 16">
  <path
											d="M2 2A2 2 0 0 0 .05 3.555L8 8.414l7.95-4.859A2 2 0 0 0 14 2zm-2 9.8V4.698l5.803 3.546zm6.761-2.97-6.57 4.026A2 2 0 0 0 2 14h6.256A4.5 4.5 0 0 1 8 12.5a4.49 4.49 0 0 1 1.606-3.446l-.367-.225L8 9.586zM16 9.671V4.697l-5.803 3.546.338.208A4.5 4.5 0 0 1 12.5 8c1.414 0 2.675.652 3.5 1.671" />
  <path
											d="M15.834 12.244c0 1.168-.577 2.025-1.587 2.025-.503 0-1.002-.228-1.12-.648h-.043c-.118.416-.543.643-1.015.643-.77 0-1.259-.542-1.259-1.434v-.529c0-.844.481-1.4 1.26-1.4.585 0 .87.333.953.63h.03v-.568h.905v2.19c0 .272.18.42.411.42.315 0 .639-.415.639-1.39v-.118c0-1.277-.95-2.326-2.484-2.326h-.04c-1.582 0-2.64 1.067-2.64 2.724v.157c0 1.867 1.237 2.654 2.57 2.654h.045c.507 0 .935-.07 1.18-.18v.731c-.219.1-.643.175-1.237.175h-.044C10.438 16 9 14.82 9 12.646v-.214C9 10.36 10.421 9 12.485 9h.035c2.12 0 3.314 1.43 3.314 3.034zm-4.04.21v.227c0 .586.227.8.581.8.31 0 .564-.17.564-.743v-.367c0-.516-.275-.708-.572-.708-.346 0-.573.245-.573.791" />
</svg>
									futboldebarrio@gmail.com
								</div>
								<div class="col-sm-2 col-md-2 cabeceraArriba">
									<svg xmlns="http://www.w3.org/2000/svg" width="1.2vw"
										height="1.2vw" fill="currentColor"
										style="margin-right: 4px; color: white;"
										class="bi bi-telephone-fill" viewBox="0 0 16 16">
  <path fill-rule="evenodd"
											d="M1.885.511a1.745 1.745 0 0 1 2.61.163L6.29 2.98c.329.423.445.974.315 1.494l-.547 2.19a.68.68 0 0 0 .178.643l2.457 2.457a.68.68 0 0 0 .644.178l2.189-.547a1.75 1.75 0 0 1 1.494.315l2.306 1.794c.829.645.905 1.87.163 2.611l-1.034 1.034c-.74.74-1.846 1.065-2.877.702a18.6 18.6 0 0 1-7.01-4.42 18.6 18.6 0 0 1-4.42-7.009c-.362-1.03-.037-2.137.703-2.877z" />
</svg>
									653435344
								</div>
								<div class="col-sm-2 col-md-2 cabeceraArriba "
									style="justify-content: left;">
									<a href="">
										<button type="button" class="botonCabeceraContactar">CONTACTAR</button>
									</a>
								</div>
								<div class="col-sm-2 col-md-2 cabeceraArriba">
									<a href=""> <svg xmlns="http://www.w3.org/2000/svg"
											width="1.4vw" height="1.2vw" fill="currentColor"
											style="margin-right: 3px; color: white;" bi
											bi-instagram" viewBox="0 0 16 16">
  <path
												d="M8 0C5.829 0 5.556.01 4.703.048 3.85.088 3.269.222 2.76.42a3.9 3.9 0 0 0-1.417.923A3.9 3.9 0 0 0 .42 2.76C.222 3.268.087 3.85.048 4.7.01 5.555 0 5.827 0 8.001c0 2.172.01 2.444.048 3.297.04.852.174 1.433.372 1.942.205.526.478.972.923 1.417.444.445.89.719 1.416.923.51.198 1.09.333 1.942.372C5.555 15.99 5.827 16 8 16s2.444-.01 3.298-.048c.851-.04 1.434-.174 1.943-.372a3.9 3.9 0 0 0 1.416-.923c.445-.445.718-.891.923-1.417.197-.509.332-1.09.372-1.942C15.99 10.445 16 10.173 16 8s-.01-2.445-.048-3.299c-.04-.851-.175-1.433-.372-1.941a3.9 3.9 0 0 0-.923-1.417A3.9 3.9 0 0 0 13.24.42c-.51-.198-1.092-.333-1.943-.372C10.443.01 10.172 0 7.998 0zm-.717 1.442h.718c2.136 0 2.389.007 3.232.046.78.035 1.204.166 1.486.275.373.145.64.319.92.599s.453.546.598.92c.11.281.24.705.275 1.485.039.843.047 1.096.047 3.231s-.008 2.389-.047 3.232c-.035.78-.166 1.203-.275 1.485a2.5 2.5 0 0 1-.599.919c-.28.28-.546.453-.92.598-.28.11-.704.24-1.485.276-.843.038-1.096.047-3.232.047s-2.39-.009-3.233-.047c-.78-.036-1.203-.166-1.485-.276a2.5 2.5 0 0 1-.92-.598 2.5 2.5 0 0 1-.6-.92c-.109-.281-.24-.705-.275-1.485-.038-.843-.046-1.096-.046-3.233s.008-2.388.046-3.231c.036-.78.166-1.204.276-1.486.145-.373.319-.64.599-.92s.546-.453.92-.598c.282-.11.705-.24 1.485-.276.738-.034 1.024-.044 2.515-.045zm4.988 1.328a.96.96 0 1 0 0 1.92.96.96 0 0 0 0-1.92m-4.27 1.122a4.109 4.109 0 1 0 0 8.217 4.109 4.109 0 0 0 0-8.217m0 1.441a2.667 2.667 0 1 1 0 5.334 2.667 2.667 0 0 1 0-5.334" />
</svg>
									</a> <a href=""> <svg xmlns="http://www.w3.org/2000/svg"
											width="1.4vw" height="1.2vw" fill="currentColor"
											style="margin-right: 3px; color: white;"
											class="bi bi-twitter-x" viewBox="0 0 16 16">
  <path
												d="M12.6.75h2.454l-5.36 6.142L16 15.25h-4.937l-3.867-5.07-4.425 5.07H.316l5.733-6.57L0 .75h5.063l3.495 4.633L12.601.75Zm-.86 13.028h1.36L4.323 2.145H2.865z" />
</svg>
									</a> <a href=""> <svg xmlns="http://www.w3.org/2000/svg"
											width="1.4vw" height="1.4vw" fill="currentColor"
											style="margin-right: 3px; color: white;"
											class="bi bi-youtube" viewBox="0 0 16 16">
  <path
												d="M8.051 1.999h.089c.822.003 4.987.033 6.11.335a2.01 2.01 0 0 1 1.415 1.42c.101.38.172.883.22 1.402l.01.104.022.26.008.104c.065.914.073 1.77.074 1.957v.075c-.001.194-.01 1.108-.082 2.06l-.008.105-.009.104c-.05.572-.124 1.14-.235 1.558a2.01 2.01 0 0 1-1.415 1.42c-1.16.312-5.569.334-6.18.335h-.142c-.309 0-1.587-.006-2.927-.052l-.17-.006-.087-.004-.171-.007-.171-.007c-1.11-.049-2.167-.128-2.654-.26a2.01 2.01 0 0 1-1.415-1.419c-.111-.417-.185-.986-.235-1.558L.09 9.82l-.008-.104A31 31 0 0 1 0 7.68v-.123c.002-.215.01-.958.064-1.778l.007-.103.003-.052.008-.104.022-.26.01-.104c.048-.519.119-1.023.22-1.402a2.01 2.01 0 0 1 1.415-1.42c.487-.13 1.544-.21 2.654-.26l.17-.007.172-.006.086-.003.171-.007A100 100 0 0 1 7.858 2zM6.4 5.209v4.818l4.157-2.408z" />
</svg>
									</a> <a href=""> <svg xmlns="http://www.w3.org/2000/svg"
											width="1.4vw" height="1.2vw" fill="currentColor"
											style="margin-right: 3px; color: white;"
											class="bi bi-facebook" viewBox="0 0 16 16">
  <path
												d="M16 8.049c0-4.446-3.582-8.05-8-8.05C3.58 0-.002 3.603-.002 8.05c0 4.017 2.926 7.347 6.75 7.951v-5.625h-2.03V8.05H6.75V6.275c0-2.017 1.195-3.131 3.022-3.131.876 0 1.791.157 1.791.157v1.98h-1.009c-.993 0-1.303.621-1.303 1.258v1.51h2.218l-.354 2.326H9.25V16c3.824-.604 6.75-3.934 6.75-7.951" />
</svg>
									</a> <a href=""> <svg xmlns="http://www.w3.org/2000/svg"
											width="1.4vw" height="1.2vw" fill="currentColor"
											style="margin-right: 3px; color: white;" class="bi bi-tiktok"
											viewBox="0 0 16 16">
  <path
												d="M9 0h1.98c.144.715.54 1.617 1.235 2.512C12.895 3.389 13.797 4 15 4v2c-1.753 0-3.07-.814-4-1.829V11a5 5 0 1 1-5-5v2a3 3 0 1 0 3 3z" />
</svg>
									</a>
								</div>

							</div>
						</div>
						<!-- fila medio -->
						<div class="col-sm-12 col-md-12 "
							style="border-left: 2px solid black; border-right: 2px solid black;">
							<div class="row">
								<div class="col-sm-6 col-md-6 cabeceraMedioTitulo">
									<span style="color: #d4af37; margin-right: 7px;"> / </span>
									FUTBOL DE BARRIO <span
										style="color: #c0c0c0; margin-left: 7px;"> /</span>
								</div>
								<div class="col-sm-3 col-md-3 cabeceraMedio">
									<svg xmlns="http://www.w3.org/2000/svg" width="1.3vw"
										height="1.3vw" fill="currentColor" style="margin-right: 0.7vw"
										class="bi bi-search" viewBox="0 0 16 16">
  <path
											d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0" />
</svg>
									<div class="barra-busqueda ">
										<input type="text" placeholder=""
											style="width: 16vw; height: 1.65vw; border-radius: 50px; display: flex; justify-content: left; align-items: center; font-size: 1vw; margin-right: 0.7vw">
									</div>


								</div>
								<div class="col-sm-3 col-md-3 cabeceraMedio"
									style="text-decoration: underline;">
									<a href="" class="letraCabeceraMedio">BIENVENIDO: Carlos</a>
								</div>

							</div>
						</div>
					</div>
				</div>
				<!-- fila de abajo -->
				<div class="col-sm-12 col-md-12  "
					style="border: solid 2px black; background-color: #004000; box-shadow: 0px 4px 8px -4px rgba(0, 0, 0, 0.45);">
					<div class="row " style="background-color: #004000;">
						<!-- columna iquierda -->
						<div class="col-sm-5 col-md-5 ">
							<div class="row  ">
								<div class="col-sm-1 col-md-1 cabeceraAbajo  "></div>
								<div class="col-sm-3 col-md-3 cabeceraAbajo ">
									<a href="" class="letraCabeceraAbajo" style="color: #d4af37;">INICIO</a>
								</div>
								<div class="col-sm-4 col-md-4 cabeceraAbajo ">
									<a href="" class="letraCabeceraAbajo">ALQUILERES</a>
								</div>
								<div class="col-sm-3 col-md-3 cabeceraAbajo ">
									<a href="" class="letraCabeceraAbajo">EVENTOS</a>
								</div>
								<div class="col-sm-1 col-md-1 cabeceraAbajo"></div>

							</div>
						</div>

						<!-- columna derecha -->
						<div class="col-sm-7 col-md-7 ">

							<div class="row">
								<div class="col-sm-1 col-md-1 cabeceraAbajo "></div>
								<div class="col-sm-3 col-md-3 cabeceraAbajo ">
									<a href="" class="letraCabeceraAbajo">MI CLUB</a>
								</div>
								<div class="col-sm-3 col-md-3 cabeceraAbajo ">
									<a href="" class="letraCabeceraAbajo">MARCADORES</a>
								</div>
								<div class="col-sm-3 col-md-3 cabeceraAbajo ">
									<a href="" class="letraCabeceraAbajo">DESAFIOS</a>
								</div>
								<div class="col-sm-2 col-md-2 cabeceraAbajo">
									<div class="dropdown">
										<button class="btn btn-secondary " type="button"
											data-bs-toggle="dropdown" aria-expanded="false"
											style="background-color: #004000; width: 4vw; height: 2.3vw; border-radius: 5px; display: flex; justify-content: center; align-items: center; padding: 0;">
											<svg xmlns="http://www.w3.org/2000/svg" fill="currentColor"
												class="bi bi-list letraCabeceraAbajo" viewBox="0 0 16 16"
												style="width: 3vw; height: 2.2vw; color: white;">
				<path fill-rule="evenodd"
													d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5" />
			</svg>
										</button>
										<ul class="dropdown-menu dropdown-menu-dark"
											style="min-width: 12vw; font-size: 1.2vw; background-color: #003300; border-radius: 5px;">
											<li><a class="dropdown-item active" href="#"
												style="color: white; background-color: #005500;">Idioma
											</a></li>
											<li><a class="dropdown-item" href="#"
												style="color: white;">Ayuda </a></li>
											<li><a class="dropdown-item" href="#"
												style="color: white;">Configuración </a></li>
											<li>
												<hr class="dropdown-divider" style="border-color: #006600;">
											</li>
											<li><a class="dropdown-item" href="#"
												style="color: white;">Cerrar sesión </a></li>
										</ul>
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</header>

	<main style="background-color: rgba(223, 234, 213, 0.5);">
	
	
	
	
	
	<!-- CONTENEDOR MARCADORES -->
		<div class="container-fluid" id="marcadorContainer">
    <div class="row">
        <div class="col-md-12 col-sm-12 mx-auto">
            <div class="row">
                
                 <div class="col-md-2 col-sm-2 mx-auto"></div>
                <div class="col-md-3 col-sm-3 mx-auto " style="margin-bottom:16vh; margin-top:24vh; display: flex; justify-content: flex-end; align-items: center;">
                    <button id="botonMarcadoresClubes" class="botonMarcadores p-4">
                        <img class="imagenMarcadores" src="Imagenes/clubes.PNG" alt="Clubes"> MARCADORES CLUBES
                    </button>
                </div>
                
                <!-- Espacio vacío entre los dos botones -->
                <div class="col-md-1 col-sm-1 mx-auto "></div>
                
                <!-- Columna para el botón de JUGADORES alineado a la derecha -->
                <div class="col-md-3 col-sm-3" style="margin-bottom:16vh; margin-top:24vh; display: flex; justify-content: flex-start; align-items: center;">
                    <button class="botonMarcadores p-4" id="mostrarJugadorBtn"> 
                        <img class="imagenMarcadores" src="Imagenes/futbolista.PNG" alt="Jugadores"> MARCADORES JUGADORES
                    </button>
                </div>
                 <div class="col-md-2 col-sm-2 mx-auto "></div>
            </div>
        </div>
    </div>
</div>

		<!-- TABLA CLUBES -->
		<div class="container-fluid mt-4" id="clubContainer"
			style="display: none;">
			<div class="row">

				<div class="col-md-12 col-sm-12 mx-auto mt-5 pt-3 m-1"
					style="display: flex; align-items: center; gap: 10px;">
					<button id="volverAContenido" class="mb-2 mr-auto botonFiltrar"
						style="background-color: red">Volver</button>
					<button id="mostrarFiltrosClubes" class="mb-2 mr-auto botonFiltrar"
						style="background-color: black">Mostrar Filtros</button>

				</div>

				<!-- Filtros de búsqueda (inicialmente ocultos) -->
				<div id="filtrosClubes" class="filaFiltrar"
					style="display: none; background-color: black;">
					<div class="filtroItem">
						<input type="text" id="buscarPosicion" class="inputFiltrar"
							placeholder="Buscar por posicion">
					</div>
					<div class="filtroItem">
						<input type="text" id="buscarNombre" class="inputFiltrar"
							placeholder="Buscar por Nombre">
					</div>
					<div class="filtroItem">
						<input type="text" id="buscarNjugadores" class="inputFiltrar"
							placeholder="Buscar por número de Jugadores">
					</div>
					<input type="text" id="buscarLocalidad" class="inputFiltrar"
						placeholder="Buscar por Localidad">

				</div>

				<table class="tablaDatosListaMarcadores mb-4">
					<thead style="background-color: black;">
						<tr>
							<th style="border: 1.5px solid #0d6ba1; width: 10.5%">POS</th>
							<th style="border: 1.5px solid #0d6ba1; width: 30%">CLUB</th>
							<th style="border: 1.5px solid #0d6ba1; width: 22.5%">LOCALIDAD</th>
							<th style="border: 1.5px solid #0d6ba1; width: 5%">PJ</th>
							<th style="border: 1.5px solid #0d6ba1; width: 5%">V</th>
							<th style="border: 1.5px solid #0d6ba1; width: 5%">E</th>
							<th style="border: 1.5px solid #0d6ba1; width: 5%">D</th>
							<th style="border: 1.5px solid #0d6ba1; width: 5%">GF</th>
							<th style="border: 1.5px solid #0d6ba1; width: 5%">GC</th>
							<th style="border: 1.5px solid #0d6ba1; width: 7%">UNIRSE</th>
						</tr>
					</thead>
					<tbody id="tablaClubes"></tbody>
				</table>
			</div>
		</div>
		
		
		
		
		<!-- TABLA JUGADORES -->
		
		<div class="container-fluid mt-4" id="jugadorContainer">
    <div class="row">
        <!-- Contenedor para los botones de volver y mostrar filtros -->
        <div class="col-md-12 col-sm-12 mx-auto mt-5 pt-3 m-1" style="display: flex; align-items: center; gap: 10px;">
            <button id="volverAContenido" class="mb-2 mr-auto botonFiltrar" style="background-color: red">
                Volver
            </button>
            <button id="mostrarFiltrosJugadores" class="mb-2 mr-auto botonFiltrar" style="background-color: black">
                Mostrar Filtros
            </button>
        </div>

        <!-- Filtros de búsqueda (inicialmente ocultos) -->
        <div id="filtrosJugadores" class="filaFiltrar" style="display: none; background-color: black;">
            <div class="filtroItem">
                <input type="text" id="buscarPosicion" class="inputFiltrar" placeholder="Buscar por posición">
            </div>
            <div class="filtroItem">
                <input type="text" id="buscarNombre" class="inputFiltrar" placeholder="Buscar por Nombre">
            </div>
            <div class="filtroItem">
                <input type="text" id="buscarNjugadores" class="inputFiltrar" placeholder="Buscar por número de Jugadores">
            </div>
            <input type="text" id="buscarLocalidad" class="inputFiltrar" placeholder="Buscar por Localidad">
        </div>

        <!-- Tabla de jugadores -->
        <table class="tablaDatosListaMarcadores mb-4">
            <thead style="background-color: black;">
                <tr>
                    <th style="border: 1.5px solid #0d6ba1; width: 10.5%">POS</th>
                    <th style="border: 1.5px solid #0d6ba1; width: 30%">JUGADOR</th> <!-- Cambié "CLUB" a "JUGADOR" -->
                    <th style="border: 1.5px solid #0d6ba1; width: 22.5%">LOCALIDAD</th>
                    <th style="border: 1.5px solid #0d6ba1; width: 5%">PJ</th>
                    <th style="border: 1.5px solid #0d6ba1; width: 5%">V</th>
                    <th style="border: 1.5px solid #0d6ba1; width: 5%">E</th>
                    <th style="border: 1.5px solid #0d6ba1; width: 5%">D</th>
                    <th style="border: 1.5px solid #0d6ba1; width: 5%">GF</th>
                    <th style="border: 1.5px solid #0d6ba1; width: 5%">GC</th>
                    <th style="border: 1.5px solid #0d6ba1; width: 7%">UNIRSE</th>
                </tr>
            </thead>
            <tbody id="tablaJugadoresCuerpo"></tbody> 
        </table>
    </div>
</div>
	</main>





	<footer>

		<div class="container-fluid ">
			<div class="row">
				<div class="col-md-12 col-sm-12">
					<div class="row">
						<div class="col-md-3 col-sm-3  g-4 pieDePagina p-4 pb-1  "
							style="margin-left: 2vw">
							<p style="text-decoration: underline; font-size: 1.5vw">SOBRE
								NOSOTROS</p>
							<p style="font-size: 1.05vw">Somos la plataforma líder para
								conectar jugadores, organizar torneos y reservar canchas de
								fútbol amateur. Únete a nuestra comunidad deportiva y vive la
								pasión por el fútbol.</p>
						</div>
						<div class="col-md-3 col-sm-3 g-4 pieDePagina p-4 pb-1 ">
							<p
								style="text-decoration: underline; font-family: 'Open Sans', sans-serif; font-size: 1.5vw">ENLACES
								ÚTILES</p>
							<pre
								style="text-decoration: underline; font-family: 'Open Sans', sans-serif; font-size: 1.05vw">
<a href="" style="color: white">Inicio</a>
<a href="" style="color: white">¿Qué es nuestra web?</a>
<a href="" style="color: white">Próximos eventos</a>
<a href="" style="color: white">Registrarse</a>
<a href="" style="color: white">Términos y Condiciones</a>
<a href="" style="color: white">Política de Privacidad</a> </pre>
						</div>
						<div class="col-md-3 col-sm-3 g-4 pieDePagina p-4 pb-1">
							<p style="text-decoration: underline; font-size: 1.5vw">CONTACTO</p>
							<pre
								style="font-family: 'Open Sans', sans-serif; font-size: 1.05vw">futboldebarrio@gmail.es
+34 123 456 789
Avenida mujer trabajadora
 Sevilla, España</pre>
						</div>
						<div class="col-md-2 col-sm-2 g-4 pieDePagina p-4 pb-1">
							<p
								style="text-decoration: underline; font-family: 'Open Sans', sans-serif; font-size: 1.5vw">
								SIGUENOS</p>


							<a href="" style="text-decoration: none"><svg
									xmlns="http://www.w3.org/2000/svg" width="1.8vw" height="1.6vw"
									fill="currentColor" style="color: white; margin-right: 0.5vw"
									class="bi bi-instagram" viewBox="0 0 16 16">
									<path
										d="M8 0C5.829 0 5.556.01 4.703.048 3.85.088 3.269.222 2.76.42a3.9 3.9 0 0 0-1.417.923A3.9 3.9 0 0 0 .42 2.76C.222 3.268.087 3.85.048 4.7.01 5.555 0 5.827 0 8.001c0 2.172.01 2.444.048 3.297.04.852.174 1.433.372 1.942.205.526.478.972.923 1.417.444.445.89.719 1.416.923.51.198 1.09.333 1.942.372C5.555 15.99 5.827 16 8 16s2.444-.01 3.298-.048c.851-.04 1.434-.174 1.943-.372a3.9 3.9 0 0 0 1.416-.923c.445-.445.718-.891.923-1.417.197-.509.332-1.09.372-1.942C15.99 10.445 16 10.173 16 8s-.01-2.445-.048-3.299c-.04-.851-.175-1.433-.372-1.941a3.9 3.9 0 0 0-.923-1.417A3.9 3.9 0 0 0 13.24.42c-.51-.198-1.092-.333-1.943-.372C10.443.01 10.172 0 7.998 0zm-.717 1.442h.718c2.136 0 2.389.007 3.232.046.78.035 1.204.166 1.486.275.373.145.64.319.92.599s.453.546.598.92c.11.281.24.705.275 1.485.039.843.047 1.096.047 3.231s-.008 2.389-.047 3.232c-.035.78-.166 1.203-.275 1.485a2.5 2.5 0 0 1-.599.919c-.28.28-.546.453-.92.598-.28.11-.704.24-1.485.276-.843.038-1.096.047-3.232.047s-2.39-.009-3.233-.047c-.78-.036-1.203-.166-1.485-.276a2.5 2.5 0 0 1-.92-.598 2.5 2.5 0 0 1-.6-.92c-.109-.281-.24-.705-.275-1.485-.038-.843-.046-1.096-.046-3.233s.008-2.388.046-3.231c.036-.78.166-1.204.276-1.486.145-.373.319-.64.599-.92s.546-.453.92-.598c.282-.11.705-.24 1.485-.276.738-.034 1.024-.044 2.515-.045zm4.988 1.328a.96.96 0 1 0 0 1.92.96.96 0 0 0 0-1.92m-4.27 1.122a4.109 4.109 0 1 0 0 8.217 4.109 4.109 0 0 0 0-8.217m0 1.441a2.667 2.667 0 1 1 0 5.334 2.667 2.667 0 0 1 0-5.334" />
</svg> </a> <a href="" style="text-decoration: none"><svg
									xmlns="http://www.w3.org/2000/svg" width="1.8vw" height="1.6vw"
									fill="currentColor"
									style="color: white; margin-right: 0.5vw; text-decoration: none"
									class="bi bi-twitter-x" viewBox="0 0 16 16">
  <path
										d="M12.6.75h2.454l-5.36 6.142L16 15.25h-4.937l-3.867-5.07-4.425 5.07H.316l5.733-6.57L0 .75h5.063l3.495 4.633L12.601.75Zm-.86 13.028h1.36L4.323 2.145H2.865z" />
</svg> </a> <a href="" style="text-decoration: none"><svg
									xmlns="http://www.w3.org/2000/svg" width="1.8vw" height="1.8vw"
									fill="currentColor" style="color: white; margin-right: 0.5vw;"
									class="bi bi-youtube" viewBox="0 0 16 16">
  <path
										d="M8.051 1.999h.089c.822.003 4.987.033 6.11.335a2.01 2.01 0 0 1 1.415 1.42c.101.38.172.883.22 1.402l.01.104.022.26.008.104c.065.914.073 1.77.074 1.957v.075c-.001.194-.01 1.108-.082 2.06l-.008.105-.009.104c-.05.572-.124 1.14-.235 1.558a2.01 2.01 0 0 1-1.415 1.42c-1.16.312-5.569.334-6.18.335h-.142c-.309 0-1.587-.006-2.927-.052l-.17-.006-.087-.004-.171-.007-.171-.007c-1.11-.049-2.167-.128-2.654-.26a2.01 2.01 0 0 1-1.415-1.419c-.111-.417-.185-.986-.235-1.558L.09 9.82l-.008-.104A31 31 0 0 1 0 7.68v-.123c.002-.215.01-.958.064-1.778l.007-.103.003-.052.008-.104.022-.26.01-.104c.048-.519.119-1.023.22-1.402a2.01 2.01 0 0 1 1.415-1.42c.487-.13 1.544-.21 2.654-.26l.17-.007.172-.006.086-.003.171-.007A100 100 0 0 1 7.858 2zM6.4 5.209v4.818l4.157-2.408z" />
</svg> </a> <a href="" style="text-decoration: none"> <svg
									xmlns="http://www.w3.org/2000/svg" width="1.8vw" height="1.6vw"
									fill="currentColor" style="color: white; margin-right: 0.5vw"
									class="bi bi-facebook" viewBox="0 0 16 16">
  <path
										d="M16 8.049c0-4.446-3.582-8.05-8-8.05C3.58 0-.002 3.603-.002 8.05c0 4.017 2.926 7.347 6.75 7.951v-5.625h-2.03V8.05H6.75V6.275c0-2.017 1.195-3.131 3.022-3.131.876 0 1.791.157 1.791.157v1.98h-1.009c-.993 0-1.303.621-1.303 1.258v1.51h2.218l-.354 2.326H9.25V16c3.824-.604 6.75-3.934 6.75-7.951" />
</svg>
							</a> <a href="" style="text-decoration: none"> <svg
									xmlns="http://www.w3.org/2000/svg" width="1.8vw" height="1.6vw"
									fill="currentColor" style="color: white;" class="bi bi-tiktok"
									viewBox="0 0 16 16">
  <path
										d="M9 0h1.98c.144.715.54 1.617 1.235 2.512C12.895 3.389 13.797 4 15 4v2c-1.753 0-3.07-.814-4-1.829V11a5 5 0 1 1-5-5v2a3 3 0 1 0 3 3z" />
</svg>
							</a>



						</div>
					</div>
				</div>
				<div class="col-md-12 col-sm-12 pieDePagina p-1 mt-4"
					style="border-top: 1px solid white;">
					<p style="font-size: 1.8vw; margin-top: 1.4vw; color: #d4af37">©
						2024 futboldebarrio.com | Todos los derechos reservados</p>

				</div>
			</div>
		</div>

	</footer>



	<script>
	
	//MOSTRAR CONTENDOR PULSADO EN LA CABECERA
	document.addEventListener("DOMContentLoaded", function () {
	const marcadorContainer = document.getElementById('marcadorContainer');
	 const mostrarMarcadorBtn = document.getElementById('mostrarMarcadorBtn');
	 
	 function mostrarContenedor(mostrar) {
	        // Ocultar todos los contenedores
	        mostrarContainer.style.display = "none";
	       
	        // Mostrar solo el contenedor seleccionado
	        mostrar.style.display = "block";
	    }
	 
	 mostrarMArcadorBtn.addEventListener('click', function (event) {
	        event.preventDefault();
	        mostrarContenedor(marcadorContainer);
	    });
	  // Mostrar la sección de inicio al cargar la página
	    mostrarContenedor(inicioContainer);

	    // Obtener todos los enlaces
	    const enlaces = document.querySelectorAll("a");

	    // Función para resaltar el enlace clicado y restablecer los demás
	    function resaltarEnlace(event) {
	        event.preventDefault(); // Evita que la página se recargue

	        enlaces.forEach(function (enlace) {
	            enlace.style.color = ""; // Restablece el color original
	        });

	        this.style.color = "#d4af37"; // Resalta el enlace clicado con color amarillo
	    }

	    // Añadir el evento de clic a cada enlace
	    enlaces.forEach(function (enlace) {
	        enlace.addEventListener("click", resaltarEnlace);
	    });
	});
	
 
 // METODO PARA MOSTRAR CONTENIDO DE LA TABLA CLUBES 
        document.addEventListener('DOMContentLoaded', function() {
            console.log('DOM completamente cargado');
            
            let clubes = [
                { club: "FC Barcelona", localidad: "Barcelona", PJ: 20, V: 14, E: 4, D: 2, GF: 38, GC: 18 },
                { club: "Real Madrid", localidad: "Madrid", PJ: 20, V: 13, E: 5, D: 2, GF: 42, GC: 20 },
                { club: "Atlético Madrid", localidad: "Madrid", PJ: 20, V: 12, E: 5, D: 3, GF: 36, GC: 19 },
                { club: "Sevilla FC", localidad: "Sevilla", PJ: 20, V: 12, E: 6, D: 2, GF: 35, GC: 17 },
                { club: "Valencia CF", localidad: "Valencia", PJ: 20, V: 10, E: 5, D: 5, GF: 30, GC: 22 },
                { club: "Real Sociedad", localidad: "San Sebastián", PJ: 20, V: 9, E: 6, D: 5, GF: 28, GC: 24 },
                { club: "Real Betis", localidad: "Sevilla", PJ: 20, V: 8, E: 7, D: 5, GF: 25, GC: 20 },
                { club: "Villarreal CF", localidad: "Villarreal", PJ: 20, V: 8, E: 5, D: 7, GF: 24, GC: 22 },
                { club: "Celta de Vigo", localidad: "Vigo", PJ: 20, V: 7, E: 6, D: 7, GF: 23, GC: 25 },
                { club: "Athletic Club", localidad: "Bilbao", PJ: 20, V: 7, E: 6, D: 7, GF: 20, GC: 22 },
                { club: "Granada CF", localidad: "Granada", PJ: 20, V: 6, E: 6, D: 8, GF: 18, GC: 24 },
                { club: "Espanyol", localidad: "Barcelona", PJ: 20, V: 6, E: 5, D: 9, GF: 17, GC: 26 },
                { club: "Getafe CF", localidad: "Getafe", PJ: 20, V: 5, E: 6, D: 9, GF: 15, GC: 25 },
                { club: "Alavés", localidad: "Vitoria", PJ: 20, V: 5, E: 4, D: 11, GF: 13, GC: 27 },
                { club: "Levante UD", localidad: "Valencia", PJ: 20, V: 4, E: 5, D: 11, GF: 12, GC: 28 },
                { club: "Mallorca", localidad: "Palma", PJ: 20, V: 3, E: 6, D: 11, GF: 11, GC: 29 },
                { club: "Cádiz CF", localidad: "Cádiz", PJ: 20, V: 3, E: 5, D: 12, GF: 10, GC: 30 },
                { club: "Osasuna", localidad: "Pamplona", PJ: 20, V: 2, E: 6, D: 12, GF: 9, GC: 31 },
                { club: "Elche CF", localidad: "Elche", PJ: 20, V: 2, E: 5, D: 13, GF: 8, GC: 32 }
               
            ];
            
            let jugadores = [
                { jugador: "Lionel Messi", localidad: "Rosario", PJ: 20, V: 14, E: 4, D: 2, GF: 38, GC: 18 },
                { jugador: "Cristiano Ronaldo", localidad: "Funchal", PJ: 20, V: 13, E: 5, D: 2, GF: 42, GC: 20 },
                { jugador: "Kylian Mbappé", localidad: "Paris", PJ: 20, V: 12, E: 5, D: 3, GF: 36, GC: 19 },
                { jugador: "Neymar Jr.", localidad: "Mogi das Cruzes", PJ: 20, V: 12, E: 6, D: 2, GF: 35, GC: 17 },
                { jugador: "Robert Lewandowski", localidad: "Warsaw", PJ: 20, V: 10, E: 5, D: 5, GF: 30, GC: 22 },
                { jugador: "Erling Haaland", localidad: "Leeds", PJ: 20, V: 9, E: 6, D: 5, GF: 28, GC: 24 },
                { jugador: "Luka Modrić", localidad: "Zadar", PJ: 20, V: 8, E: 7, D: 5, GF: 25, GC: 20 },
                { jugador: "Kevin De Bruyne", localidad: "Ghent", PJ: 20, V: 8, E: 5, D: 7, GF: 24, GC: 22 },
                { jugador: "Sergio Ramos", localidad: "Camas", PJ: 20, V: 7, E: 6, D: 7, GF: 23, GC: 25 },
                { jugador: "Luka Jovic", localidad: "Novi Sad", PJ: 20, V: 7, E: 6, D: 7, GF: 20, GC: 22 },
            ];

            // Ordenar los clubes por el número de victorias en orden descendente
            clubes.sort((a, b) => b.V - a.V);

            const tablaCuerpo = document.getElementById("tablaClubes");

            let startIndex = 0;
            const batchSize = 15; // Cargar 15 clubes al principio
            const maxClubs = 100; // Limitar a 100 clubes
            
            let startIndexJugadores = 0;
            const batchSizeJugadores = 10;
            const maxJugadores = jugadores.length;  // Definir el número máximo de jugadores

            const tablaJugadoresCuerpo = document.getElementById("tablaJugadoresCuerpo");

            // Función para cargar los clubes en la tabla
            function cargarClubes() {
                if (startIndex >= maxClubs) return; // No cargar más de 100 clubes

                let endIndex = Math.min(startIndex + batchSize, clubes.length, maxClubs);

                for (let i = startIndex; i < endIndex; i++) {
                    const club = clubes[i];
                    let row = document.createElement("tr");
                   

                    // Crear las celdas individualmente
                    let posCell = document.createElement("td");
                    posCell.textContent = i + 1;
                    row.appendChild(posCell);
                    posCell.style.fontSize = '1.25vw';

                    let clubCell = document.createElement("td");
                    clubCell.textContent = club.club;
                    row.appendChild(clubCell);
                    clubCell.style.fontSize = '1.25vw';

                    
                    let localidadCell = document.createElement("td");
                    localidadCell.textContent = club.localidad;
                    row.appendChild(localidadCell);
                    localidadCell.style.fontSize = '1.25vw';

                    let pjCell = document.createElement("td");
                    pjCell.textContent = club.PJ;
                    row.appendChild(pjCell);
                    pjCell.style.fontSize = '1.25vw';

                    let vCell = document.createElement("td");
                    vCell.textContent = club.V;
                    row.appendChild(vCell);
                    vCell.style.fontSize = '1.25vw';

                    let eCell = document.createElement("td");
                    eCell.textContent = club.E;
                    row.appendChild(eCell);
                    eCell.style.fontSize = '1.25vw';

                    let dCell = document.createElement("td");
                    dCell.textContent = club.D;
                    row.appendChild(dCell);
                    dCell.style.fontSize = '1.25vw';

                    let gfCell = document.createElement("td");
                    gfCell.textContent = club.GF;
                    row.appendChild(gfCell);
                    gfCell.style.fontSize = '1.25vw';

                    let gcCell = document.createElement("td");
                    gcCell.textContent = club.GC;
                    row.appendChild(gcCell);
                    gcCell.style.fontSize = '1.25vw';
                    
                    let unirseCell = document.createElement("td");
                    let botonUnirse = document.createElement("button");
                    botonUnirse.innerHTML = `
                      <svg xmlns="http://www.w3.org/2000/svg" width="2vw" height="1.5vw" fill="currentColor" class="bi bi-box-arrow-in-right" viewBox="0 0 16 16">
                        <path fill-rule="evenodd" d="M6 3.5a.5.5 0 0 1 .5-.5h8a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-.5.5h-8a.5.5 0 0 1-.5-.5v-2a.5.5 0 0 0-1 0v2A1.5 1.5 0 0 0 6.5 14h8a1.5 1.5 0 0 0 1.5-1.5v-9A1.5 1.5 0 0 0 14.5 2h-8A1.5 1.5 0 0 0 5 3.5v2a.5.5 0 0 0 1 0z"/>
                        <path fill-rule="evenodd" d="M11.854 8.354a.5.5 0 0 0 0-.708l-3-3a.5.5 0 1 0-.708.708L10.293 7.5H1.5a.5.5 0 0 0 0 1h8.793l-2.147 2.146a.5.5 0 0 0 .708.708z"/>
                      </svg>`;

                      botonUnirse.classList.add("botonUnirse");
                    document.body.appendChild(botonUnirse);
                    botonUnirse.addEventListener("click", function() {
                        alert(`Te has unido a ${club.club}`);
                    });

                    unirseCell.appendChild(botonUnirse);
                    row.appendChild(unirseCell);

                  
                    tablaCuerpo.appendChild(row);
                }

                startIndex += batchSize;
            }

            // Cargar los primeros 15 clubes al cargar la página
            cargarClubes();

            // Detectar el scroll y cargar más clubes cuando se llegue al final
            window.addEventListener("scroll", function() {
                if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 50) {
                    cargarClubes();
                }
            });
            
            
            function cargarJugadores() {
                if (startIndexJugadores >= maxJugadores) return;

                let endIndex = Math.min(startIndexJugadores + batchSizeJugadores, jugadores.length, maxJugadores);

                for (let i = startIndexJugadores; i < endIndex; i++) {
                    const jugador = jugadores[i];
                    let row = document.createElement("tr");

                    let posCell = document.createElement("td");
                    posCell.textContent = i + 1;
                    row.appendChild(posCell);

                    let jugadorCell = document.createElement("td");
                    jugadorCell.textContent = jugador.jugador;
                    row.appendChild(jugadorCell);

                    let localidadCell = document.createElement("td");
                    localidadCell.textContent = jugador.localidad;
                    row.appendChild(localidadCell);

                    let pjCell = document.createElement("td");
                    pjCell.textContent = jugador.PJ;
                    row.appendChild(pjCell);

                    let vCell = document.createElement("td");
                    vCell.textContent = jugador.V;
                    row.appendChild(vCell);

                    let eCell = document.createElement("td");
                    eCell.textContent = jugador.E;
                    row.appendChild(eCell);

                    let dCell = document.createElement("td");
                    dCell.textContent = jugador.D;
                    row.appendChild(dCell);

                    let gfCell = document.createElement("td");
                    gfCell.textContent = jugador.GF;
                    row.appendChild(gfCell);

                    let gcCell = document.createElement("td");
                    gcCell.textContent = jugador.GC;
                    row.appendChild(gcCell);

                    let unirseCell = document.createElement("td");
                    let botonUnirse = document.createElement("button");
                    botonUnirse.innerHTML = `
                        <svg xmlns="http://www.w3.org/2000/svg" width="2.5vw" height="2vw" fill="currentColor" class="bi bi-box-arrow-in-right" viewBox="0 0 16 16">
                            <path fill-rule="evenodd" d="M6 3.5a.5.5 0 0 1 .5-.5h8a.5.5 0 0 1 .5.5v9a.5.5 0 0 1-.5.5h-8a.5.5 0 0 1-.5-.5v-2a.5.5 0 0 0-1 0v2A1.5 1.5 0 0 0 6.5 14h8a1.5 1.5 0 0 0 1.5-1.5v-9A1.5 1.5 0 0 0 14.5 2h-8A1.5 1.5 0 0 0 5 3.5v2a.5.5 0 0 0 1 0z"/>
                            <path fill-rule="evenodd" d="M11.854 8.354a.5.5 0 0 0 0-.708l-3-3a.5.5 0 1 0-.708.708L10.293 7.5H1.5a.5.5 0 0 0 0 1h8.793l-2.147 2.146a.5.5 0 0 0 .708.708z"/>
                        </svg>`;
                    unirseCell.appendChild(botonUnirse);
                    row.appendChild(unirseCell);

                    tablaJugadoresCuerpo.appendChild(row);
                }

                startIndexJugadores += batchSizeJugadores;
            }

            // Cargar los primeros jugadores al cargar la página
            cargarJugadores();

            // Detectar el scroll y cargar más jugadores cuando se llegue al final
            window.addEventListener("scroll", function() {
                if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 50) {
                    cargarJugadores();
                }
            });
        
   

            // Lógica para mostrar/ocultar los filtros
            document.getElementById("mostrarFiltrosClubes").addEventListener("click", function() {
                const filtros = document.getElementById("filtrosClubes");
                const boton = document.getElementById("mostrarFiltrosClubes");

                if (filtros.style.display === "none") {
                    filtros.style.display = "flex"; // Mostrar los filtros
                    boton.textContent = "Ocultar Filtros"; // Cambiar el texto del botón
                } else {
                    filtros.style.display = "none"; // Ocultar los filtros
                    boton.textContent = "Mostrar Filtros"; // Cambiar el texto del botón
                }
            });
        });
 
        const botonMarcadoresClubes = document.getElementById('botonMarcadoresClubes');
        const clubContainer = document.getElementById('clubContainer');
        const volverAContenido = document.getElementById('volverAContenido');  // Botón para volver a la vista anterior
        const mainContent = document.querySelector('main');  // Capturamos todo el contenido principal

        // Función para mostrar solo la tabla de clubes
        botonMarcadoresClubes.addEventListener('click', function() {
          // Ocultar las secciones que no son la tabla
          const contenidoPrincipal = mainContent.querySelectorAll('.container-fluid');
          contenidoPrincipal.forEach(item => {
            item.style.display = 'none';  // Ocultar todas las secciones, excepto la tabla
          });

          // Mostrar solo el contenedor de la tabla
          clubContainer.style.display = 'block';  // Mostrar la tabla
        });

        // Función para volver a la vista anterior
        volverAContenido.addEventListener('click', function() {
          // Mostrar el contenido principal nuevamente
          const contenidoPrincipal = mainContent.querySelectorAll('.container-fluid');
          contenidoPrincipal.forEach(item => {
            item.style.display = 'block';  // Mostrar las secciones
          });

          // Ocultar la tabla
          clubContainer.style.display = 'none';  // Ocultar la tabla
        });
    </script>
	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
</body>
</html>