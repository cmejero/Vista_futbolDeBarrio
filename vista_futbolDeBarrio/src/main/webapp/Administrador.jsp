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


	<header>
		<!-- Contenedor principal de -->
		<div class="container-fluid fixed-top ">
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
						<div class="col-sm-2 col-md-2"></div>

						<div class="col-sm-10 col-md-10 ">
							<div class="row">
								<div class="col-md-2 col-sm-2 cabeceraAbajo">
									<a href="" class="letraCabeceraAbajo" style="color: #d4af37;">INICIO</a>
								</div>
								<div class="col-md-2 col-sm-2 cabeceraAbajo">
									<a href="" id="mostrarUsuariosBtn" class="letraCabeceraAbajo">USUARIOS</a>
								</div>
								<div class="col-md-3 col-sm-3 cabeceraAbajo">
									<a href="" class="letraCabeceraAbajo">INSTALACIONES</a>
								</div>
								<div class="col-md-2 col-sm-2 cabeceraAbajo">
									<a href="" class="letraCabeceraAbajo">CLUBES</a>
								</div>
								<div class="col-md-2 col-sm-2 cabeceraAbajo">
									<a href="" class="letraCabeceraAbajo">ARBITROS</a>
								</div>
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>
	</header>

	<main style="background-color: rgba(223, 234, 213, 0.5);">
		<div class="container-fluid mt-5 pt-2">


			<!-- MAIN -->
			<div class="row igual-altura">
				<!-- MENU LATERAL -->
				<div class="col-md-2 col-sm-2 menuLateral">
					<pre
						style="text-align: left; font-family: 'Open Sans', sans-serif; overflow: hidden;">
<b><span style="font-size: 1.6vw"><svg xmlns="http://www.w3.org/2000/svg" style="margin-right:0.4vw"width="1.65vw" height="1.75vw" fill="currentColor" class="bi bi-clipboard-check-fill" viewBox="0 0 16 16">
  <path d="M6.5 0A1.5 1.5 0 0 0 5 1.5v1A1.5 1.5 0 0 0 6.5 4h3A1.5 1.5 0 0 0 11 2.5v-1A1.5 1.5 0 0 0 9.5 0zm3 1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-3a.5.5 0 0 1-.5-.5v-1a.5.5 0 0 1 .5-.5z"/>
  <path d="M4 1.5H3a2 2 0 0 0-2 2V14a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V3.5a2 2 0 0 0-2-2h-1v1A2.5 2.5 0 0 1 9.5 5h-3A2.5 2.5 0 0 1 4 2.5zm6.854 7.354-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 0 1 .708-.708L7.5 10.793l2.646-2.647a.5.5 0 0 1 .708.708"/>
</svg>Gestiones</span></b>
 - <a href="" style="text-decoration: none;" class="letraMenuLateral">Eventos</a>
 - <a href="" style="text-decoration: none;" class="letraMenuLateral">Reservas</a>
 - <a href="" style="text-decoration: none;" class="letraMenuLateral">Pagos</a>
 - <a href="" style="text-decoration: none;" class="letraMenuLateral">Estadísticas</a>
<b><span style="font-size: 1.6vw"><svg xmlns="http://www.w3.org/2000/svg" style="margin-right:0.4vw" width="1.5vw" height="1.75vw" fill="currentColor" class="bi bi-headset" viewBox="0 0 16 16">
  <path d="M8 1a5 5 0 0 0-5 5v1h1a1 1 0 0 1 1 1v3a1 1 0 0 1-1 1H3a1 1 0 0 1-1-1V6a6 6 0 1 1 12 0v6a2.5 2.5 0 0 1-2.5 2.5H9.366a1 1 0 0 1-.866.5h-1a1 1 0 1 1 0-2h1a1 1 0 0 1 .866.5H11.5A1.5 1.5 0 0 0 13 12h-1a1 1 0 0 1-1-1V8a1 1 0 0 1 1-1h1V6a5 5 0 0 0-5-5"/>
</svg>Soporte</span></b>
 - <a href="" style="text-decoration: none;" class="letraMenuLateral">Configuración</a>
 - <a href="" style="text-decoration: none;" class="letraMenuLateral">Ticket</a>
 - <a href="" style="text-decoration: none;" class="letraMenuLateral">Feedback</a>
<b><span style="font-size: 1.6vw"><svg xmlns="http://www.w3.org/2000/svg" style="margin-right:0.4vw" width="1.65vw" height="1.75vw" fill="currentColor" class="bi bi-cart-check-fill" viewBox="0 0 16 16">
  <path d="M.5 1a.5.5 0 0 0 0 1h1.11l.401 1.607 1.498 7.985A.5.5 0 0 0 4 12h1a2 2 0 1 0 0 4 2 2 0 0 0 0-4h7a2 2 0 1 0 0 4 2 2 0 0 0 0-4h1a.5.5 0 0 0 .491-.408l1.5-8A.5.5 0 0 0 14.5 3H2.89l-.405-1.621A.5.5 0 0 0 2 1zM6 14a1 1 0 1 1-2 0 1 1 0 0 1 2 0m7 0a1 1 0 1 1-2 0 1 1 0 0 1 2 0m-1.646-7.646-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 1 1 .708-.708L8 8.293l2.646-2.647a.5.5 0 0 1 .708.708"/>
</svg>Promociones</span></b>
 - <a href="" style="text-decoration: none;" class="letraMenuLateral">Descuentos</a>
 - <a href="" style="text-decoration: none;" class="letraMenuLateral">Boletines</a>
<b><span style="text-decoration: underline"><a href=""
								style="text-decoration: none; font-size: 1.35vw"
								class="letraMenuLateral">Cerrar sesión</a></span></b>
	
					</pre>
				</div>


				<!-- CONTENIDO -->
				<div class="col-md-10 col-sm-10 contenido ">
					<div class="row">

						<!-- USUARIOS -->
						<div class="col-md-11 col-sm-11  mx-auto m-5" id="userContainer" style="display: none;">

							<button  id="mostrarFiltrosBtn" class="mb-3 mr-auto botonFiltrar">Mostrar
								Filtros</button>

							<!-- Filtros de búsqueda (inicialmente ocultos) -->

							<div id="filtros" class="filaFiltrar">
								<div class="filtroItem">
									<label for="buscarId" class="labelFiltrar"><b>-Buscar
											por ID:</b></label> <input type="text" id="buscarId" class="inputFiltrar"
										placeholder="Buscar por ID">
								</div>

								<div class="filtroItem">
									<label for="buscarNombre" class="labelFiltrar"><b>-Buscar
											por Nombre:</b></label> <input type="text" id="buscarNombre"
										class="inputFiltrar" placeholder="Buscar por Nombre">
								</div>

								<div class="filtroItem">
									<label for="buscarCorreo" class="labelFiltrar"><b>-Buscar
											por Correo:</b></label> <input type="text" id="buscarCorreo"
										class="inputFiltrar" placeholder="Buscar por Correo">
								</div>
							</div>


							<table class="tablaDatos">
								<thead style="background-color: #c33214;">
									<tr>
										<th style="border: 1.8px solid #8a210b; width: 10%">ID</th>
										<th style="border: 1.8px solid #8a210b; width: 37.5'%">NOMBRE
											JUGADOR</th>
										<th style="border: 1.8px solid #8a210b; width: 37.5%">EMAIL
											JUGADOR</th>
										<th style="border: 1.8px solid #8a210b; width: 15%">OPCIONES</th>
									</tr>
								</thead>
								<tbody id="tablaCuerpo">
									<tr>
										<td>1000000000</td>
										<td>Fila 1, Col 2</td>
										<td>Fila 1, Col 3</td>
										<td><button class="botonEliminarAdmin">
												<svg xmlns="http://www.w3.org/2000/svg" width="15"
													height="15" fill="currentColor" class="bi bi-trash3-fill"
													style="color: #c33214; margin-bottom: 0.1vw"
													viewBox="0 0 16 16">
  <path
														d="M11 1.5v1h3.5a.5.5 0 0 1 0 1h-.538l-.853 10.66A2 2 0 0 1 11.115 16h-6.23a2 2 0 0 1-1.994-1.84L2.038 3.5H1.5a.5.5 0 0 1 0-1H5v-1A1.5 1.5 0 0 1 6.5 0h3A1.5 1.5 0 0 1 11 1.5m-5 0v1h4v-1a.5.5 0 0 0-.5-.5h-3a.5.5 0 0 0-.5.5M4.5 5.029l.5 8.5a.5.5 0 1 0 .998-.06l-.5-8.5a.5.5 0 1 0-.998.06m6.53-.528a.5.5 0 0 0-.528.47l-.5 8.5a.5.5 0 0 0 .998.058l.5-8.5a.5.5 0 0 0-.47-.528M8 4.5a.5.5 0 0 0-.5.5v8.5a.5.5 0 0 0 1 0V5a.5.5 0 0 0-.5-.5" />
</svg>
											</button>
											<button class="botonModificarAdmin">
												<svg xmlns="http://www.w3.org/2000/svg" width="15"
													height="15" fill="currentColor" class="bi bi-pencil-square"
													style="color: orange; margin-bottom: 0.1vw"
													viewBox="0 0 16 16">
  <path
														d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z" />
  <path fill-rule="evenodd"
														d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5z" />
</svg>
											</button></td>
									</tr>
									<tr>
										<td>Fila 2, Col 1</td>
										<td>Fila 2, Col 2</td>
										<td>Fila 2, Col 3</td>
										<td>Fila 2, Col 4</td>
									</tr>
									<tr>
										<td>Fila 3, Col 1</td>
										<td>Fila 3, Col 2</td>
										<td>Fila 3, Col 3</td>
										<td>Fila 3, Col 4</td>
									</tr>
									<tr>
										<td>Fila 4, Col 1</td>
										<td>Fila 4, Col 2</td>
										<td>Fila 4, Col 3</td>
										<td>Fila 4, Col 4</td>
									</tr>
									<tr>
										<td>Fila 5, Col 1</td>
										<td>Fila 5, Col 2</td>
										<td>Fila 5, Col 3</td>
										<td>Fila 5, Col 4</td>
									</tr>
									<tr>
										<td>Fila 5, Col 1</td>
										<td>Fila 5, Col 2</td>
										<td>Fila 5, Col 3</td>
										<td>Fila 5, Col 4</td>
									</tr>
									<tr>
										<td>Fila 5, Col 1</td>
										<td>Fila 5, Col 2</td>
										<td>Fila 5, Col 3</td>
										<td>Fila 5, Col 4</td>
									</tr>
									<tr>
										<td>Fila 5, Col 1</td>
										<td>Fila 5, Col 2</td>
										<td>Fila 5, Col 3</td>
										<td>Fila 5, Col 4</td>
									</tr>
									<tr>
										<td>Fila 5, Col 1</td>
										<td>Fila 5, Col 2</td>
										<td>Fila 5, Col 3</td>
										<td>Fila 5, Col 4</td>
									</tr>
									<tr>
										<td>Fila 5, Col 1</td>
										<td>Fila 5, Col 2</td>
										<td>Fila 5, Col 3</td>
										<td>Fila 5, Col 4</td>
									</tr>
								</tbody>
							</table>
							<div class="contenedorPaginacion">
								<button id="botonAnterior" class="botonPaginacion"
									onclick="cambiarPagina(-1)">< Anterior</button>
								<span id="paginaActual"><b style="font-size: 1.2vw">1</b></span>
								<button id="botonSiguiente" class="botonPaginacion"
									onclick="cambiarPagina(1)">Siguiente ></button>
							</div>

						</div>







						<!-- INSTALACIONES -->
						<div class="col-md-11 col-sm-11  mx-auto m-5">

							<button id="mostrarFiltros" class="mb-3 mr-auto botonFiltrar">Mostrar
								Filtros</button>

							<!-- Filtros de búsqueda (inicialmente ocultos) -->

							<div id="filtros" class="filaFiltrar">
								<div class="filtroItem">
									<label for="buscarId" class="labelFiltrar"><b>-Buscar
											por ID:</b></label> <input type="text" id="buscarId" class="inputFiltrar"
										placeholder="Buscar por ID">
								</div>

								<div class="filtroItem">
									<label for="buscarNombre" class="labelFiltrar"><b>-Buscar
											por Nombre:</b></label> <input type="text" id="buscarNombre"
										class="inputFiltrar" placeholder="Buscar por Nombre">
								</div>

								<div class="filtroItem">
									<label for="buscarCorreo" class="labelFiltrar"><b>-Buscar
											por Correo:</b></label> <input type="text" id="buscarCorreo"
										class="inputFiltrar" placeholder="Buscar por Correo">
								</div>
							</div>


							<table class="tablaDatos">
								<thead style="background-color: #37b137;">
									<tr>
										<th style="border: 1.8px solid #0d730d; width: 10%">ID</th>
										<th style="border: 1.8px solid #0d730d; width: 37.5'%">NOMBRE
											INSTALACIÓN</th>
										<th style="border: 1.8px solid #0d730d; width: 37.5%">EMAIL
											INSTALACIÓN</th>
										<th style="border: 1.8px solid #0d730d; width: 15%">OPCIONES</th>
									</tr>
								</thead>
								<tbody id="tablaCuerpo">
									<tr>
										<td>1000000000</td>
										<td>Fila 1, Col 2</td>
										<td>Fila 1, Col 3</td>
										<td><button class="botonEliminarAdmin">
												<svg xmlns="http://www.w3.org/2000/svg" width="15"
													height="15" fill="currentColor" class="bi bi-trash3-fill"
													style="color: #c33214; margin-bottom: 0.1vw"
													viewBox="0 0 16 16">
  <path
														d="M11 1.5v1h3.5a.5.5 0 0 1 0 1h-.538l-.853 10.66A2 2 0 0 1 11.115 16h-6.23a2 2 0 0 1-1.994-1.84L2.038 3.5H1.5a.5.5 0 0 1 0-1H5v-1A1.5 1.5 0 0 1 6.5 0h3A1.5 1.5 0 0 1 11 1.5m-5 0v1h4v-1a.5.5 0 0 0-.5-.5h-3a.5.5 0 0 0-.5.5M4.5 5.029l.5 8.5a.5.5 0 1 0 .998-.06l-.5-8.5a.5.5 0 1 0-.998.06m6.53-.528a.5.5 0 0 0-.528.47l-.5 8.5a.5.5 0 0 0 .998.058l.5-8.5a.5.5 0 0 0-.47-.528M8 4.5a.5.5 0 0 0-.5.5v8.5a.5.5 0 0 0 1 0V5a.5.5 0 0 0-.5-.5" />
</svg>
											</button>
											<button class="botonModificarAdmin">
												<svg xmlns="http://www.w3.org/2000/svg" width="15"
													height="15" fill="currentColor" class="bi bi-pencil-square"
													style="color: orange; margin-bottom: 0.1vw"
													viewBox="0 0 16 16">
  <path
														d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z" />
  <path fill-rule="evenodd"
														d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5z" />
</svg>
											</button></td>
									</tr>
									<tr>
										<td>Fila 2, Col 1</td>
										<td>Fila 2, Col 2</td>
										<td>Fila 2, Col 3</td>
										<td>Fila 2, Col 4</td>
									</tr>
									<tr>
										<td>Fila 3, Col 1</td>
										<td>Fila 3, Col 2</td>
										<td>Fila 3, Col 3</td>
										<td>Fila 3, Col 4</td>
									</tr>
									<tr>
										<td>Fila 4, Col 1</td>
										<td>Fila 4, Col 2</td>
										<td>Fila 4, Col 3</td>
										<td>Fila 4, Col 4</td>
									</tr>
									<tr>
										<td>Fila 5, Col 1</td>
										<td>Fila 5, Col 2</td>
										<td>Fila 5, Col 3</td>
										<td>Fila 5, Col 4</td>
									</tr>
									<tr>
										<td>Fila 5, Col 1</td>
										<td>Fila 5, Col 2</td>
										<td>Fila 5, Col 3</td>
										<td>Fila 5, Col 4</td>
									</tr>
									<tr>
										<td>Fila 5, Col 1</td>
										<td>Fila 5, Col 2</td>
										<td>Fila 5, Col 3</td>
										<td>Fila 5, Col 4</td>
									</tr>
									<tr>
										<td>Fila 5, Col 1</td>
										<td>Fila 5, Col 2</td>
										<td>Fila 5, Col 3</td>
										<td>Fila 5, Col 4</td>
									</tr>
									<tr>
										<td>Fila 5, Col 1</td>
										<td>Fila 5, Col 2</td>
										<td>Fila 5, Col 3</td>
										<td>Fila 5, Col 4</td>
									</tr>
									<tr>
										<td>Fila 5, Col 1</td>
										<td>Fila 5, Col 2</td>
										<td>Fila 5, Col 3</td>
										<td>Fila 5, Col 4</td>
									</tr>
								</tbody>
							</table>
							<div class="contenedorPaginacion">
								<button id="botonAnterior" class="botonPaginacion"
									onclick="cambiarPagina(-1)">< Anterior</button>
								<span id="paginaActual"><b style="font-size: 1.2vw">1</b></span>
								<button id="botonSiguiente" class="botonPaginacion"
									onclick="cambiarPagina(1)">Siguiente ></button>
							</div>

						</div>







						<!-- CLUBES -->
						<div class="col-md-11 col-sm-11  mx-auto m-5">

							<button id="mostrarFiltros" class="mb-3 mr-auto botonFiltrar">Mostrar
								Filtros</button>

							<!-- Filtros de búsqueda (inicialmente ocultos) -->

							<div id="filtros" class="filaFiltrar">
								<div class="filtroItem">
									<label for="buscarId" class="labelFiltrar"><b>-Buscar
											por ID:</b></label> <input type="text" id="buscarId" class="inputFiltrar"
										placeholder="Buscar por ID">
								</div>

								<div class="filtroItem">
									<label for="buscarNombre" class="labelFiltrar"><b>-Buscar
											por Nombre:</b></label> <input type="text" id="buscarNombre"
										class="inputFiltrar" placeholder="Buscar por Nombre">
								</div>

								<div class="filtroItem">
									<label for="buscarCorreo" class="labelFiltrar"><b>-Buscar
											por Correo:</b></label> <input type="text" id="buscarCorreo"
										class="inputFiltrar" placeholder="Buscar por Correo">
								</div>
							</div>


							<table class="tablaDatos">
								<thead style="background-color: #2783b8;">
									<tr>
										<th style="border: 1.8px solid #0d6ba1; width: 10%">ID</th>
										<th style="border: 1.8px solid #0d6ba1; width: 37.5'%">NOMBRE
											CLUB</th>
										<th style="border: 1.8px solid #0d6ba1; width: 37.5%">EMAIL
											CLUB</th>
										<th style="border: 1.8px solid #0d6ba1; width: 15%">OPCIONES</th>
									</tr>
								</thead>
								<tbody id="tablaCuerpo">
									<tr>
										<td>1000000000</td>
										<td>Fila 1, Col 2</td>
										<td>Fila 1, Col 3</td>
										<td><button class="botonEliminarAdmin">
												<svg xmlns="http://www.w3.org/2000/svg" width="15"
													height="15" fill="currentColor" class="bi bi-trash3-fill"
													style="color: #c33214; margin-bottom: 0.1vw"
													viewBox="0 0 16 16">
  <path
														d="M11 1.5v1h3.5a.5.5 0 0 1 0 1h-.538l-.853 10.66A2 2 0 0 1 11.115 16h-6.23a2 2 0 0 1-1.994-1.84L2.038 3.5H1.5a.5.5 0 0 1 0-1H5v-1A1.5 1.5 0 0 1 6.5 0h3A1.5 1.5 0 0 1 11 1.5m-5 0v1h4v-1a.5.5 0 0 0-.5-.5h-3a.5.5 0 0 0-.5.5M4.5 5.029l.5 8.5a.5.5 0 1 0 .998-.06l-.5-8.5a.5.5 0 1 0-.998.06m6.53-.528a.5.5 0 0 0-.528.47l-.5 8.5a.5.5 0 0 0 .998.058l.5-8.5a.5.5 0 0 0-.47-.528M8 4.5a.5.5 0 0 0-.5.5v8.5a.5.5 0 0 0 1 0V5a.5.5 0 0 0-.5-.5" />
</svg>
											</button>
											<button class="botonModificarAdmin">
												<svg xmlns="http://www.w3.org/2000/svg" width="15"
													height="15" fill="currentColor" class="bi bi-pencil-square"
													style="color: orange; margin-bottom: 0.1vw"
													viewBox="0 0 16 16">
  <path
														d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z" />
  <path fill-rule="evenodd"
														d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5z" />
</svg>
											</button></td>
									</tr>
									<tr>
										<td>Fila 2, Col 1</td>
										<td>Fila 2, Col 2</td>
										<td>Fila 2, Col 3</td>
										<td>Fila 2, Col 4</td>
									</tr>
									<tr>
										<td>Fila 3, Col 1</td>
										<td>Fila 3, Col 2</td>
										<td>Fila 3, Col 3</td>
										<td>Fila 3, Col 4</td>
									</tr>
									<tr>
										<td>Fila 4, Col 1</td>
										<td>Fila 4, Col 2</td>
										<td>Fila 4, Col 3</td>
										<td>Fila 4, Col 4</td>
									</tr>
									<tr>
										<td>Fila 5, Col 1</td>
										<td>Fila 5, Col 2</td>
										<td>Fila 5, Col 3</td>
										<td>Fila 5, Col 4</td>
									</tr>
									<tr>
										<td>Fila 5, Col 1</td>
										<td>Fila 5, Col 2</td>
										<td>Fila 5, Col 3</td>
										<td>Fila 5, Col 4</td>
									</tr>
									<tr>
										<td>Fila 5, Col 1</td>
										<td>Fila 5, Col 2</td>
										<td>Fila 5, Col 3</td>
										<td>Fila 5, Col 4</td>
									</tr>
									<tr>
										<td>Fila 5, Col 1</td>
										<td>Fila 5, Col 2</td>
										<td>Fila 5, Col 3</td>
										<td>Fila 5, Col 4</td>
									</tr>
									<tr>
										<td>Fila 5, Col 1</td>
										<td>Fila 5, Col 2</td>
										<td>Fila 5, Col 3</td>
										<td>Fila 5, Col 4</td>
									</tr>
									<tr>
										<td>Fila 5, Col 1</td>
										<td>Fila 5, Col 2</td>
										<td>Fila 5, Col 3</td>
										<td>Fila 5, Col 4</td>
									</tr>
								</tbody>
							</table>
							<div class="contenedorPaginacion">
								<button id="botonAnterior" class="botonPaginacion"
									onclick="cambiarPagina(-1)">< Anterior</button>
								<span id="paginaActual"><b style="font-size: 1.2vw">1</b></span>
								<button id="botonSiguiente" class="botonPaginacion"
									onclick="cambiarPagina(1)">Siguiente ></button>
							</div>

						</div>





					</div>
				</div>
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
				<div class="col-md-12 col-sm-12 pieDePagina p-1"
					style="border-top: 1px solid white;">
					<p style="font-size: 1.8vw; margin-top: 1.4vw; color: #d4af37">©
						2024 futboldebarrio.com | Todos los derechos reservados</p>

				</div>
			</div>
		</div>

	</footer>
	
	
	
	<script>
	// Mostrar/Ocultar los filtros al hacer clic en el botón
	document.getElementById("mostrarFiltrosBtn").addEventListener("click", function() {
		const filtros = document.getElementById("filtros");
		const boton = document.getElementById("mostrarFiltrosBtn");

		if (filtros.style.display === "none") {
			filtros.style.display = "flex"; // Mostrar los filtros
			boton.textContent = "Ocultar Filtros"; // Cambiar el texto del botón
		} else {
			filtros.style.display = "none"; // Ocultar los filtros
			boton.textContent = "Mostrar Filtros"; // Cambiar el texto del botón
		}
	});

	// Función para filtrar la tabla
	function filtrarTabla(idFiltro, columnaIndex) {
		const valorFiltro = document.getElementById(idFiltro).value.toLowerCase();
		const filas = document.querySelectorAll(".tablaDatos tbody tr");

		filas.forEach(function(fila) {
			const celdas = fila.getElementsByTagName("td");
			const textoCelda = celdas[columnaIndex].textContent.toLowerCase();
			if (textoCelda.includes(valorFiltro)) {
				fila.style.display = "";
			} else {
				fila.style.display = "none";
			}
		});
	}

	// Agregar evento de filtro para cada campo
	document.getElementById("buscarId").addEventListener("input", function() {
		filtrarTabla("buscarId", 0); // Filtro por columna ID (columna 0)
	});

	document.getElementById("buscarNombre").addEventListener("input", function() {
		filtrarTabla("buscarNombre", 1); // Filtro por columna Nombre (columna 1)
	});

	document.getElementById("buscarCorreo").addEventListener("input", function() {
		filtrarTabla("buscarCorreo", 2); // Filtro por columna Correo (columna 2)
	});


	// CODIGO PARA BOTONES ANTERIOR Y SIGUIENTE Y ESCRIBIR EN LA TBODY
	let datos = [];
	for (let i = 1; i <= 50; i++) {
		datos.push({ id: i, nombre: "Jugador " + i, correo: "jugador" + i + "@correo.com", opciones: "Editar | Eliminar" });
	}

	let paginaActual = 1;
	const filasPorPagina = 10;
	const cuerpoTabla = document.getElementById("cuerpoTabla");

	function mostrarTabla() {
		cuerpoTabla.innerHTML = "";
		let inicio = (paginaActual - 1) * filasPorPagina;
		let fin = inicio + filasPorPagina;
		let datosPagina = datos.slice(inicio, fin);

		datosPagina.forEach((item) => {
			let fila = `<tr>
				<td>${item.id}</td>
				<td>${item.nombre}</td>
				<td>${item.correo}</td>
				<td>${item.opciones}</td>
			</tr>`;
			cuerpoTabla.innerHTML += fila;
		});

		document.getElementById("paginaActual").textContent = paginaActual;
		document.getElementById("botonAnterior").disabled = paginaActual === 1;
		document.getElementById("botonSiguiente").disabled = fin >= datos.length;
	}

	function cambiarPagina(cambio) {
		paginaActual += cambio;
		mostrarTabla();
	}


	// Obtener el botón y el contenedor de la tabla de usuarios
	const mostrarUsuariosBtn = document.getElementById('mostrarUsuariosBtn');
	const userContainer = document.getElementById('userContainer');

	// Añadir un evento de clic al botón para mostrar/ocultar los usuarios
	mostrarUsuariosBtn.addEventListener('click', function(event) {
		event.preventDefault(); // Evita el comportamiento predeterminado del botón (si lo tiene)

		// Cambiar el estado de visibilidad del contenedor de usuarios
		if (userContainer.style.display === 'none' || userContainer.style.display === '') {
			userContainer.style.display = 'block'; // Mostrar el contenedor de usuarios
		} else {
			userContainer.style.display = 'none'; // Ocultar el contenedor de usuarios
		}
	});

	
	// Obtener todos los enlaces
	const enlaces = document.querySelectorAll("a");

	// Función para resaltar el enlace clicado y restablecer los demás
	function resaltarEnlace() {
	    // Restablecer el color de todos los enlaces
	    enlaces.forEach(function(enlace) {
	        enlace.style.backgroundColor = ''; // Elimina el color de fondo de todos
	    });

	    // Resaltar el enlace clicado
	    this.style.backgroundColor = 'yellow'; // Resalta con color amarillo
	}

	// Añadir el evento de clic a cada enlace
	enlaces.forEach(function(enlace) {
	    enlace.addEventListener("click", resaltarEnlace);
	});
</script>



</body>
</html>