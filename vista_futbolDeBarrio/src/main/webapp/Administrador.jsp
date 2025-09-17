
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FUTBOL DE BARRIO</title>

<!-- Estilos CSS -->
<link rel="stylesheet" href="Css/Estilo.css">

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">

<!-- Bootstrap Icons -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
	rel="stylesheet">

<!-- DataTables CSS -->
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">

<!-- jQuery (Debe estar antes de Bootstrap y DataTables) -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- Bootstrap JS -->
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<!-- DataTables JS -->
<script
	src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>

<!-- Chart.js (Solo si lo necesitas) -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>

<body>


	<header>
		<!-- Contenedor principal de -->
		<div class="container-fluid fixed-top ">
			<div class="row ">
				<div class="col-sm-12 col-md-12  d-sm-block d-md-block d-none">
					<div class="row">

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
													style="margin-right: 3px; color: white;"
													class="bi bi-tiktok" viewBox="0 0 16 16">
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
												height="1.3vw" fill="currentColor"
												style="margin-right: 0.7vw" class="bi bi-search"
												viewBox="0 0 16 16">
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
											<a href="#inicioInicioBtn" id="mostrarInicioBtn"
												class="letraCabeceraAbajo">INICIO</a>
										</div>
										<div class="col-md-2 col-sm-2 cabeceraAbajo">
											<a href="#" id="mostrarUsuariosBtn"
												class="letraCabeceraAbajo">USUARIOS</a>
										</div>
										<div class="col-md-3 col-sm-3 cabeceraAbajo">
											<a href="#" id="mostrarInstalacionesBtn"
												class="letraCabeceraAbajo">INSTALACIONES</a>
										</div>
										<div class="col-md-2 col-sm-2 cabeceraAbajo">
											<a href="#" id="mostrarClubesBtn" class="letraCabeceraAbajo">CLUBES</a>
										</div>
										<div class="col-md-2 col-sm-2 cabeceraAbajo">
											<a href="#" id="mostrarArbitrosBtn"
												class="letraCabeceraAbajo">ARBITROS</a>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>


				<!-- HEADER COL -->

				<div class="  d-sm-none d-md-none col-12 d-block ">
					<div class="row align-items-stretch" style="height: 55px;">
						<!-- columna logo -->
						<div class="d-sm-none d-md-none col-2 d-block logo  "
							style="background-color: white; border: 2px solid black; border-top: none">
							<img src="Imagenes/LOGOWEB.PNG"></img>
						</div>

						<!-- Columna derecha que se divide en 2 filas -->
						<div class="d-sm-none d-md-none col-10 d-block ">
							<div class="row ">

								<!-- fila arriba -->
								<div class="d-sm-none d-md-none col-12 d-block"
									style="border: 2px solid black; border-bottom: none; border-left: none; border-top: 1px solid black;">
									<div class="row">
										<div
											class="d-sm-none d-md-none col-8 d-block cabeceraMedioTituloX">
											<span style="color: #d4af37; margin-right: 1.5vw;"> /
											</span> FUTBOL DE BARRIO <span
												style="color: #c0c0c0; margin-left: 7px;"> /</span>
										</div>
										<div
											class="d-sm-none d-md-none col-4 d-block cabeceraMedio  d-flex justify-content-center align-items-center">
											<svg xmlns="http://www.w3.org/2000/svg" width="1.7vw"
												height="1.7vw" fill="currentColor"
												style="margin-right: 0.7vw" class="bi bi-search"
												viewBox="0 0 16 16">
  <path
													d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0" />
</svg>
											<div class="barra-busqueda ">
												<input type="text" placeholder=""
													style="width: 18vw; height: 2.5vw; border-radius: 50px; display: flex; justify-content: left; align-items: center; font-size: 1.75vw; margin-right: 0.7vw">
											</div>
										</div>

									</div>
								</div>
							</div>


							<!-- fila de abajo -->
							<div class="row p-1"
								style="border-bottom: solid 2.4px black; border-top: solid 2px black; border-right: solid 2px black; border-left: none; background-color: #004000; box-shadow: 0px 4px 8px -4px rgba(0, 0, 0, 0.45);">

								<!-- columna izquierda: INICIO -->


								<!-- columna medio: buscador -->
								<div
									class="col-10 d-flex justify-content-center align-items-center">
									<div class="row">
										<div class="col-2  cabeceraAbajo">
											<a href="#inicioInicioBtn" id="mostrarInicioBtn"
												class="letraCabeceraAbajo">INICIO</a>
										</div>
										<div class="col-2  cabeceraAbajo">
											<a href="#" id="mostrarUsuariosBtn"
												class="letraCabeceraAbajo">USUARIOS</a>
										</div>
										<div class=" col-3 cabeceraAbajo">
											<a href="#" id="mostrarInstalacionesBtn"
												class="letraCabeceraAbajo">INSTALACIONES</a>
										</div>
										<div class="col-2 cabeceraAbajo">
											<a href="#" id="mostrarClubesBtn" class="letraCabeceraAbajo">CLUBES</a>
										</div>
										<div class=" col-2 cabeceraAbajo">
											<a href="#" id="mostrarArbitrosBtn"
												class="letraCabeceraAbajo">ARBITROS</a>
										</div>
									</div>
								</div>

								<!-- columna derecha: menú desplegable -->
								<div
									class="col-2 d-flex justify-content-end align-items-center ">
									<div class="dropdown">
										<button class="btn btn-secondary " type="button"
											data-bs-toggle="dropdown" aria-expanded="false"
											style="background-color: #004000; width: 7.5vw; height: 3.9vw; border-radius: 5px; display: flex; justify-content: center; align-items: center; padding: 0;">
											<svg xmlns="http://www.w3.org/2000/svg" fill="currentColor"
												class="bi bi-list letraCabeceraAbajo" viewBox="0 0 16 16"
												style="width: 6.5vw; height: 2.5vw; color: white;">
										<path fill-rule="evenodd"
													d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5m0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5" />
									</svg>
										</button>
										<ul class="dropdown-menu dropdown-menu-dark"
											style="min-width: 12vw; font-size: 2.2vw; background-color: #003300; border-radius: 5px; width: 25vw">

											<li><a class="dropdown-item " href="Index.jsp"
												style="color: white;">Eventos </a></li>
											<li><a class="dropdown-item " href="Index.jsp"
												style="color: white;">Reservas </a></li>
											<li><a class="dropdown-item " href="Index.jsp"
												style="color: white;">Pagos </a></li>
											<li><a class="dropdown-item " href="Index.jsp"
												style="color: white;">Estadisticas </a></li>


											<li>
												<hr class="dropdown-divider" style="border-color: #006600;">
											</li>
											<li><a class="dropdown-item" href="#"
												style="color: white;">Configuración </a></li>
											<li><a class="dropdown-item " href="#"
												style="color: white;">Ticket </a></li>
											<li><a class="dropdown-item" href="#"
												style="color: white;">Feedback </a></li>

											<li>
												<hr class="dropdown-divider" style="border-color: #006600;">
											</li>
											<li><a class="dropdown-item" href="#"
												style="color: white;">Descuentos </a></li>
											<li><a class="dropdown-item " href="#"
												style="color: white;">Boletines </a></li>

											<li>
												<hr class="dropdown-divider" style="border-color: #006600;">
											</li>
											<li><a class="dropdown-item" href="logout" style="color: white;">Cerrar sesión</a></li>




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
		<div class="container-fluid mt-5 pt-2">


			<!-- MAIN -->
			<div class="row igual-altura">
				<!-- MENU LATERAL -->
				<div
					class="col-md-2 col-sm-2 d-md-block d-sm-block d-none menuLateral">
					<pre
						style="text-align: left; font-family: 'Open Sans', sans-serif; overflow: hidden;">
<b><span style="font-size: 1.6vw"><svg
									xmlns="http://www.w3.org/2000/svg" style="margin-right: 0.4vw"
									width="1.65vw" height="1.75vw" fill="currentColor"
									class="bi bi-clipboard-check-fill" viewBox="0 0 16 16">
  <path
										d="M6.5 0A1.5 1.5 0 0 0 5 1.5v1A1.5 1.5 0 0 0 6.5 4h3A1.5 1.5 0 0 0 11 2.5v-1A1.5 1.5 0 0 0 9.5 0zm3 1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-3a.5.5 0 0 1-.5-.5v-1a.5.5 0 0 1 .5-.5z" />
  <path
										d="M4 1.5H3a2 2 0 0 0-2 2V14a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V3.5a2 2 0 0 0-2-2h-1v1A2.5 2.5 0 0 1 9.5 5h-3A2.5 2.5 0 0 1 4 2.5zm6.854 7.354-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 0 1 .708-.708L7.5 10.793l2.646-2.647a.5.5 0 0 1 .708.708" />
</svg>Gestiones</span></b>
 - <a href="" style="text-decoration: none;" class="letraMenuLateral">Eventos</a>
 - <a href="" style="text-decoration: none;" class="letraMenuLateral">Reservas</a>
 - <a href="" style="text-decoration: none;" class="letraMenuLateral">Pagos</a>
 - <a href="" style="text-decoration: none;" class="letraMenuLateral">Estadísticas</a>
<b><span style="font-size: 1.6vw"><svg
									xmlns="http://www.w3.org/2000/svg" style="margin-right: 0.4vw"
									width="1.5vw" height="1.75vw" fill="currentColor"
									class="bi bi-headset" viewBox="0 0 16 16">
  <path
										d="M8 1a5 5 0 0 0-5 5v1h1a1 1 0 0 1 1 1v3a1 1 0 0 1-1 1H3a1 1 0 0 1-1-1V6a6 6 0 1 1 12 0v6a2.5 2.5 0 0 1-2.5 2.5H9.366a1 1 0 0 1-.866.5h-1a1 1 0 1 1 0-2h1a1 1 0 0 1 .866.5H11.5A1.5 1.5 0 0 0 13 12h-1a1 1 0 0 1-1-1V8a1 1 0 0 1 1-1h1V6a5 5 0 0 0-5-5" />
</svg>Soporte</span></b>
 - <a href="" style="text-decoration: none;" class="letraMenuLateral">Configuración</a>
 - <a href="" style="text-decoration: none;" class="letraMenuLateral">Ticket</a>
 - <a href="" style="text-decoration: none;" class="letraMenuLateral">Feedback</a>
<b><span style="font-size: 1.6vw"><svg
									xmlns="http://www.w3.org/2000/svg" style="margin-right: 0.4vw"
									width="1.65vw" height="1.75vw" fill="currentColor"
									class="bi bi-cart-check-fill" viewBox="0 0 16 16">
  <path
										d="M.5 1a.5.5 0 0 0 0 1h1.11l.401 1.607 1.498 7.985A.5.5 0 0 0 4 12h1a2 2 0 1 0 0 4 2 2 0 0 0 0-4h7a2 2 0 1 0 0 4 2 2 0 0 0 0-4h1a.5.5 0 0 0 .491-.408l1.5-8A.5.5 0 0 0 14.5 3H2.89l-.405-1.621A.5.5 0 0 0 2 1zM6 14a1 1 0 1 1-2 0 1 1 0 0 1 2 0m7 0a1 1 0 1 1-2 0 1 1 0 0 1 2 0m-1.646-7.646-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 1 1 .708-.708L8 8.293l2.646-2.647a.5.5 0 0 1 .708.708" />
</svg>Promociones</span></b>
 - <a href="" style="text-decoration: none;" class="letraMenuLateral">Descuentos</a>
 - <a href="" style="text-decoration: none;" class="letraMenuLateral">Boletines</a>
<b><span style="text-decoration: underline"><a class="dropdown-item" href="logout" style="text-decoration: none; font-size: 1.35vw">Cerrar sesión</a></span></b>
	
					</pre>
				</div>


				<!-- CONTENIDO -->
				<div class="col-md-10 col-sm-10 col-12 contenido ">
					<div class="row">


						<!-- INICIO: !NO ENTRA ES SOLO DECORACION! -->
						<div class="col-md-12 col-sm-11 mx-auto " id="inicioContainer">


							<style>
body {
	font-family: Arial, sans-serif;
	background-color: rgba(223, 234, 213, 0.5);
}

h2 {
	font-size: 2rem;
	text-decoration: underline;
	margin-bottom: 1.5rem;
}

.chart-container {
	background-color: #e0e0e0;
	padding: 1.5rem;
	box-shadow: 0 0.4rem 0.8rem rgba(0, 64, 0, 0.6), 0 -0.4rem 0.8rem
		rgba(0, 64, 0, 0.15);
	height: 300px;
	margin-bottom: 2rem;
}

@media ( max-width : 768px) {
	h2 {
		font-size: 1.5rem;
	}
	.chart-container {
		height: 250px;
		padding: 1rem;
	}
}

@media ( max-width : 576px) {
	h2 {
		font-size: 1.2rem;
	}
	.chart-container {
		height: 200px;
		padding: 0.75rem;
	}
}

canvas {
	width: 100% !important;
	height: 100% !important;
}
</style>


							<div class="container my-5">
								<div class="row justify-content-center">
									<div class="col-12 col-md-10">
										<h2 class="text-center">📊 Estadísticas Generales</h2>

										<div class="chart-container">
											<canvas id="usuariosChart"></canvas>
										</div>
										<div class="chart-container">
											<canvas id="visitasChart"></canvas>
										</div>
										<div class="chart-container">
											<canvas id="categoriasChart"></canvas>
										</div>
									</div>
								</div>
							</div>

							<script>
								function getChartOptions() {
									return {
										responsive : true,
										maintainAspectRatio : false,
										scales : {
											x : {
												ticks : {
													color : 'black'
												},
												grid : {
													color : 'black'
												}
											},
											y : {
												ticks : {
													color : 'black'
												},
												grid : {
													color : 'black'
												}
											}
										},
										plugins : {
											legend : {
												labels : {
													color : 'black'
												}
											}
										}
									};
								}

								new Chart(
										document
												.getElementById('usuariosChart'),
										{
											type : 'bar',
											data : {
												labels : [ 'Enero', 'Febrero',
														'Marzo', 'Abril',
														'Mayo', 'Junio' ],
												datasets : [ {
													label : 'USUARIOS REGISTRADOS',
													data : [ 120, 150, 180,
															210, 250, 300 ],
													backgroundColor : 'rgba(54, 162, 235, 0.6)',
													borderColor : 'rgba(54, 162, 235, 1)',
													borderWidth : 1
												} ]
											},
											options : getChartOptions()
										});

								new Chart(
										document.getElementById('visitasChart'),
										{
											type : 'line',
											data : {
												labels : [ 'Enero', 'Febrero',
														'Marzo', 'Abril',
														'Mayo', 'Junio' ],
												datasets : [ {
													label : 'VISITAS MENSUALES',
													data : [ 500, 800, 1200,
															1500, 2000, 2500 ],
													borderColor : 'rgba(255, 99, 132, 1)',
													backgroundColor : 'rgba(255, 99, 132, 0.2)',
													borderWidth : 2,
													fill : true
												} ]
											},
											options : getChartOptions()
										});

								new Chart(document
										.getElementById('categoriasChart'), {
									type : 'pie',
									data : {
										labels : [ 'Jugadores', 'Porteros',
												'Instalaciones', 'Clubes',
												'Arbitros', 'Premium' ],
										datasets : [ {
											data : [ 8000, 560, 100, 300, 200,
													1200 ],
											backgroundColor : [ '#c33214',
													'#f94300', '#37b137',
													'#2783b8', '#fff024',
													'#d4af37' ]
										} ]
									},
									options : {
										responsive : true,
										maintainAspectRatio : false,
										plugins : {
											legend : {
												labels : {
													color : 'black'
												}
											}
										}
									}
								});
							</script>
						</div>





						<!-- USUARIOS -->
						<div class="col-md-11 col-sm-11 col-12 mx-auto "
							id="usuarioContainer">
							<button id="mostrarFiltrosUsuario"
								class="mb-3 mr-auto botonFiltrar">Mostrar Filtros</button>

							<!-- Filtros de búsqueda (inicialmente ocultos) -->
							<div id="filtrosUsuario" class="filaFiltrar"
								style="display: none;">
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

							<!-- Tabla de usuarios -->
							<table class="tablaDatos">
								<thead style="background-color: #c33214;">
									<tr>
										<th style="border: 1.8px solid #8a210b; width: 8%">ID</th>
										<th style="border: 1.8px solid #8a210b; width: 10%">IMAGEN</th>
										<th style="border: 1.8px solid #8a210b; width: 36%">NOMBRE
											JUGADOR</th>
										<th style="border: 1.8px solid #8a210b; width: 36%">EMAIL
											JUGADOR</th>
										<th style="border: 1.8px solid #8a210b; width: 10%">OPCIONES</th>
									</tr>
								</thead>
								<tbody id="tablaCuerpoUsuario">
								</tbody>
							</table>

							<!-- Paginación -->
							<div class="contenedorPaginacion mb-4">
								<button id="botonAnterior" class="botonPaginacion"
									onclick="cambiarPagina(-1)">< Anterior</button>
								<span id="paginaActual"><b style="font-size: 1.2vw">1</b></span>
								<button id="botonSiguiente" class="botonPaginacion"
									onclick="cambiarPagina(1)">Siguiente ></button>
							</div>
						</div>

						<!-- Modal de Edición de Usuario -->
						<div class="modal fade" id="modalEditarUsuario" tabindex="-1"
							aria-labelledby="modalEditarUsuarioLabel" aria-hidden="true">
							<div class="modal-dialog modal-lg">
								<!-- Tamaño grande para más espacio -->
								<div class="modal-content">
									<div class="modal-header">
										<h5 class="modal-title w-100 text-center"
											id="modalEditarUsuarioLabel">
											<b>EDITAR USUARIO</b>
										</h5>
										<button type="button" class="btn-close"
											data-bs-dismiss="modal" aria-label="Cerrar"></button>
									</div>
									<div class="modal-body">
										<form id="formEditarUsuario" method="POST"
											enctype="multipart/form-data" action="usuario">
											<input type="hidden" id="idUsuario" name="idUsuario">

											<div class="container-fluid">
												<div class="row mb-3">
													<div class="col-md-6 mb-3">
														<label for="nombreCompletoUsuario"><b>Nombre
																Completo</b></label> <input type="text" name="nombreCompletoUsuario"
															class="form-control" id="nombreCompletoUsuario"
															style="border: 2px solid #aaa;">
													</div>
													<div class="col-md-6 mb-3">
														<label for="aliasUsuario"><b>Alias</b></label> <input
															type="text" name="aliasUsuario" class="form-control"
															id="aliasUsuario" style="border: 2px solid #aaa;">
													</div>
												</div>

												<div class="row mb-3">
													<div class="col-md-6 mb-3">
														<label for="emailUsuario"><b>Email</b></label> <input
															type="email" name="emailUsuario" class="form-control"
															id="emailUsuario" style="border: 2px solid #aaa;">
													</div>
													<div class="col-md-6 mb-3">
														<label for="fechaNacimientoUsuario"><b>Fecha
																de Nacimiento</b></label> <input type="date"
															name="fechaNacimientoUsuario" class="form-control"
															id="fechaNacimientoUsuario"
															style="border: 2px solid #aaa;">
													</div>
												</div>

												<div class="row mb-3">
													<div class="col-md-6 mb-3">
														<label for="telefonoUsuario"><b>Teléfono</b></label> <input
															type="text" name="telefonoUsuario" class="form-control"
															id="telefonoUsuario" style="border: 2px solid #aaa;">
													</div>
													<div class="col-md-6 mb-3">
														<label for="rolUsuario"><b>Rol</b></label> <select
															class="form-select" name="rolUsuario" id="rolUsuario"
															style="border: 2px solid #aaa;">
															<option value="Administrador">Administrador</option>
															<option value="Jugador">Jugador</option>
															<option value="Guardameta">Portero</option>
														</select>
													</div>
												</div>

												<div class="row mb-3">
													<div class="col-md-6 mb-3">
														<label for="estadoUsuario"><b>Estado</b></label> <select
															class="form-select" name="estadoUsuario"
															id="estadoUsuario" style="border: 2px solid #aaa;">
															<option value="Activo">Activo</option>
															<option value="Inactivo">Inactivo</option>
														</select>
													</div>
													<div class="col-md-6 mb-3">
														<label for="descripcionUsuario"><b>Descripción</b></label>
														<input type="text" name="descripcionUsuario"
															class="form-control" id="descripcionUsuario"
															style="border: 2px solid #aaa;">
													</div>
												</div>

												<div class="row mb-3">
													<div class="col-md-6 mb-3">
														<label for="imagenUsuario"><b>Imagen</b></label> <input
															type="file" name="imagenUsuario" class="form-control"
															id="imagenUsuario" style="border: 2px solid #aaa;">
													</div>
													<div class="col-md-6">
														<input type="hidden" id="passwordUsuario"
															name="passwordUsuario">
													</div>
												</div>
											</div>
										</form>
									</div>
									<div id="mensajeModificacion"
										style="display: none; margin-top: 1vw; font-weight: bold; text-align: center; width: 100%;"></div>
									<div class="modal-footer">
										<button type="button" class="btn btn-secondary"
											data-bs-dismiss="modal">Cancelar</button>
										<button type="submit" class="btn btn-primary"
											form="formEditarUsuario">Guardar Cambios</button>

									</div>
								</div>
							</div>
						</div>




						<!-- INSTALACIONES -->
						<div class="col-md-11 col-sm-11 col-12  mx-auto m-5"
							id="instalacionesContainer" style="display: none;">

							<button id="mostrarFiltrosInstalacion"
								class="mb-3 mr-auto botonFiltrar">Mostrar Filtros</button>

							<!-- Filtros de búsqueda (inicialmente ocultos) -->

							<div id="filtrosInstalacion" class="filaFiltrar"
								style="display: none;">
								<div class="filtroItem">
									<label for="buscarIdInstalaciones" class="labelFiltrar"><b>-Buscar
											por ID:</b></label> <input type="text" id="buscarIdInstalaciones"
										class="inputFiltrar" placeholder="Buscar por ID">
								</div>

								<div class="filtroItem">
									<label for="buscarNombreInstalaciones" class="labelFiltrar"><b>-Buscar
											por Nombre:</b></label> <input type="text" id="buscarNombreInstalaciones"
										class="inputFiltrar" placeholder="Buscar por Nombre">
								</div>

								<div class="filtroItem">
									<label for="buscarCorreoInstalaciones" class="labelFiltrar"><b>-Buscar
											por Correo:</b></label> <input type="text" id="buscarCorreoInstalaciones"
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
								<tbody id="tablaCuerpoInstalacion">

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
						<div class="col-md-11 col-sm-11 col-12  mx-auto m-5"
							id="clubContainer" style="display: none;">

							<button id="mostrarFiltrosClub" class="mb-3 mr-auto botonFiltrar">Mostrar
								Filtros</button>

							<!-- Filtros de búsqueda (inicialmente ocultos) -->

							<div id="filtrosClub" class="filaFiltrar" style="display: none;">
								<div class="filtroItem">
									<label for="buscarIdClub" class="labelFiltrar"><b>-Buscar
											por ID:</b></label> <input type="text" id="buscarIdClub"
										class="inputFiltrar" placeholder="Buscar por ID">
								</div>

								<div class="filtroItem">
									<label for="buscarNombreClub" class="labelFiltrar"><b>-Buscar
											por Nombre:</b></label> <input type="text" id="buscarNombreClub"
										class="inputFiltrar" placeholder="Buscar por Nombre">
								</div>

								<div class="filtroItem">
									<label for="buscarCorreoClub" class="labelFiltrar"><b>-Buscar
											por Correo:</b></label> <input type="text" id="buscarCorreoClub"
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
								<tbody id="tablaCuerpoClub">

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







						<!-- ARBITROS -->
						<div class="col-md-11 col-sm-11 col-12 mx-auto m-5"
							id="arbitrosContainer" style="display: none;">

							<button id="mostrarFiltrosArbitros"
								class="mb-3 mr-auto botonFiltrar">Mostrar Filtros</button>

							<!-- Filtros de búsqueda (inicialmente ocultos) -->

							<div id="filtrosArbitros" class="filaFiltrar"
								style="display: none;">
								<div class="filtroItem">
									<label for="buscarIdArbitro" class="labelFiltrar"><b>-Buscar
											por ID:</b></label> <input type="text" id="buscarIdArbitro"
										class="inputFiltrar" placeholder="Buscar por ID">
								</div>

								<div class="filtroItem">
									<label for="buscarNombreArbitro" class="labelFiltrar"><b>-Buscar
											por Nombre:</b></label> <input type="text" id="buscarNombreArbitro"
										class="inputFiltrar" placeholder="Buscar por Nombre">
								</div>

								<div class="filtroItem">
									<label for="buscarCorreoArbitro" class="labelFiltrar"><b>-Buscar
											por Correo:</b></label> <input type="text" id="buscarCorreoArbitro"
										class="inputFiltrar" placeholder="Buscar por Correo">
								</div>
							</div>

							<table class="tablaDatos">
								<thead style="background-color: #fff024">
									<tr>
										<th style="border: 1.8px solid #cbb20d; width: 10%">ID</th>
										<th style="border: 1.8px solid #cbb20d; width: 37.5'%">NOMBRE
											ARBITRO</th>
										<th style="border: 1.8px solid #cbb20d; width: 37.5%">EMAIL
											ARBITRO</th>
										<th style="border: 1.8px solid #cbb20d; width: 15%">OPCIONES</th>
									</tr>
								</thead>
								<tbody id="tablaCuerpo">
									<tr>
										<td>1000000000</td>
										<td>Fila 1, Col 2</td>
										<td>Fila 1, Col 3</td>
										<td><button class="botonEliminarAdmin"
												style="border: 1px solid black; height: 1.75vw;">
												<svg xmlns="http://www.w3.org/2000/svg" width="1vw"
													height="1vw" fill="currentColor" class="bi bi-trash3-fill"
													style="color: #c33214; margin-bottom: 0.1vw"
													viewBox="0 0 16 16">
  <path
														d="M11 1.5v1h3.5a.5.5 0 0 1 0 1h-.538l-.853 10.66A2 2 0 0 1 11.115 16h-6.23a2 2 0 0 1-1.994-1.84L2.038 3.5H1.5a.5.5 0 0 1 0-1H5v-1A1.5 1.5 0 0 1 6.5 0h3A1.5 1.5 0 0 1 11 1.5m-5 0v1h4v-1a.5.5 0 0 0-.5-.5h-3a.5.5 0 0 0-.5.5M4.5 5.029l.5 8.5a.5.5 0 1 0 .998-.06l-.5-8.5a.5.5 0 1 0-.998.06m6.53-.528a.5.5 0 0 0-.528.47l-.5 8.5a.5.5 0 0 0 .998.058l.5-8.5a.5.5 0 0 0-.47-.528M8 4.5a.5.5 0 0 0-.5.5v8.5a.5.5 0 0 0 1 0V5a.5.5 0 0 0-.5-.5" />
</svg>
											</button>
											<button class="botonModificarAdmin">
												<svg xmlns="http://www.w3.org/2000/svg" width="1vw"
													height="1vw" fill="currentColor"
													class="bi bi-pencil-square"
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
				<div class="col-md-12 col-sm-12 d-none d-md-block d-sm-block">
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
								UTILES</p>
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
				<div
					class="col-md-12 col-sm-12 d-none d-md-block d-sm-block pieDePagina p-1 mt-4"
					style="border-top: 1px solid white;">
					<p style="font-size: 1.8vw; margin-top: 1.4vw; color: #d4af37">©
						2024 futboldebarrio.com | Todos los derechos reservados</p>

				</div>




				<div class=" col-12 d-block d-md-none d-sm-none">
					<div class="row">

						<div
							class=" col-4 d-block d-md-none d-sm-none g-2 pieDePagina p-2  "
							style="color: white">
							<p
								style="text-decoration: underline; font-family: 'Open Sans', sans-serif; font-size: 2vw">ENLACES
								UTILES</p>
							<pre
								style="text-decoration: underline; color: white; font-family: 'Open Sans', sans-serif; font-size: 1.6vw">
<a href="#" style="color: white;">Inicio</a>
<a href="#" style="color: white">¿Qué es nuestra web?</a>
<a href="#" style="color: white">Próximos eventos</a>
<a href="#" style="color: white">Registrarse</a>
<a href="#" style="color: white">Términos y Condiciones</a>
<a href="#" style="color: white">Política de Privacidad</a> </pre>
						</div>
						<div
							class="col-4 d-block d-md-none d-sm-none g-2 pieDePagina p-2 ">
							<p style="text-decoration: underline; font-size: 2vw">CONTACTO</p>
							<pre
								style="font-family: 'Open Sans', sans-serif; font-size: 1.6vw">futboldebarrio@gmail.es
+34 123 456 789
Avenida mujer trabajadora
 Sevilla, España</pre>
						</div>
						<div
							class="col-4 d-block d-md-none d-sm-none g-2 pieDePagina p-2 ">
							<p
								style="text-decoration: underline; font-family: 'Open Sans', sans-serif; font-size: 2vw">
								SIGUENOS</p>


							<a href="" style="text-decoration: none"><svg
									xmlns="http://www.w3.org/2000/svg" width="2.2vw" height="2vw"
									fill="currentColor" style="color: white; margin-right: 0.5vw"
									class="bi bi-instagram" viewBox="0 0 16 16">
									<path
										d="M8 0C5.829 0 5.556.01 4.703.048 3.85.088 3.269.222 2.76.42a3.9 3.9 0 0 0-1.417.923A3.9 3.9 0 0 0 .42 2.76C.222 3.268.087 3.85.048 4.7.01 5.555 0 5.827 0 8.001c0 2.172.01 2.444.048 3.297.04.852.174 1.433.372 1.942.205.526.478.972.923 1.417.444.445.89.719 1.416.923.51.198 1.09.333 1.942.372C5.555 15.99 5.827 16 8 16s2.444-.01 3.298-.048c.851-.04 1.434-.174 1.943-.372a3.9 3.9 0 0 0 1.416-.923c.445-.445.718-.891.923-1.417.197-.509.332-1.09.372-1.942C15.99 10.445 16 10.173 16 8s-.01-2.445-.048-3.299c-.04-.851-.175-1.433-.372-1.941a3.9 3.9 0 0 0-.923-1.417A3.9 3.9 0 0 0 13.24.42c-.51-.198-1.092-.333-1.943-.372C10.443.01 10.172 0 7.998 0zm-.717 1.442h.718c2.136 0 2.389.007 3.232.046.78.035 1.204.166 1.486.275.373.145.64.319.92.599s.453.546.598.92c.11.281.24.705.275 1.485.039.843.047 1.096.047 3.231s-.008 2.389-.047 3.232c-.035.78-.166 1.203-.275 1.485a2.5 2.5 0 0 1-.599.919c-.28.28-.546.453-.92.598-.28.11-.704.24-1.485.276-.843.038-1.096.047-3.232.047s-2.39-.009-3.233-.047c-.78-.036-1.203-.166-1.485-.276a2.5 2.5 0 0 1-.92-.598 2.5 2.5 0 0 1-.6-.92c-.109-.281-.24-.705-.275-1.485-.038-.843-.046-1.096-.046-3.233s.008-2.388.046-3.231c.036-.78.166-1.204.276-1.486.145-.373.319-.64.599-.92s.546-.453.92-.598c.282-.11.705-.24 1.485-.276.738-.034 1.024-.044 2.515-.045zm4.988 1.328a.96.96 0 1 0 0 1.92.96.96 0 0 0 0-1.92m-4.27 1.122a4.109 4.109 0 1 0 0 8.217 4.109 4.109 0 0 0 0-8.217m0 1.441a2.667 2.667 0 1 1 0 5.334 2.667 2.667 0 0 1 0-5.334" />
</svg> </a> <a href="" style="text-decoration: none"><svg
									xmlns="http://www.w3.org/2000/svg" width="2.2vw" height="2vw"
									fill="currentColor"
									style="color: white; margin-right: 0.5vw; text-decoration: none"
									class="bi bi-twitter-x" viewBox="0 0 16 16">
  <path
										d="M12.6.75h2.454l-5.36 6.142L16 15.25h-4.937l-3.867-5.07-4.425 5.07H.316l5.733-6.57L0 .75h5.063l3.495 4.633L12.601.75Zm-.86 13.028h1.36L4.323 2.145H2.865z" />
</svg> </a> <a href="" style="text-decoration: none"><svg
									xmlns="http://www.w3.org/2000/svg" width="2.2vw" height="2vw"
									fill="currentColor" style="color: white; margin-right: 0.5vw;"
									class="bi bi-youtube" viewBox="0 0 16 16">
  <path
										d="M8.051 1.999h.089c.822.003 4.987.033 6.11.335a2.01 2.01 0 0 1 1.415 1.42c.101.38.172.883.22 1.402l.01.104.022.26.008.104c.065.914.073 1.77.074 1.957v.075c-.001.194-.01 1.108-.082 2.06l-.008.105-.009.104c-.05.572-.124 1.14-.235 1.558a2.01 2.01 0 0 1-1.415 1.42c-1.16.312-5.569.334-6.18.335h-.142c-.309 0-1.587-.006-2.927-.052l-.17-.006-.087-.004-.171-.007-.171-.007c-1.11-.049-2.167-.128-2.654-.26a2.01 2.01 0 0 1-1.415-1.419c-.111-.417-.185-.986-.235-1.558L.09 9.82l-.008-.104A31 31 0 0 1 0 7.68v-.123c.002-.215.01-.958.064-1.778l.007-.103.003-.052.008-.104.022-.26.01-.104c.048-.519.119-1.023.22-1.402a2.01 2.01 0 0 1 1.415-1.42c.487-.13 1.544-.21 2.654-.26l.17-.007.172-.006.086-.003.171-.007A100 100 0 0 1 7.858 2zM6.4 5.209v4.818l4.157-2.408z" />
</svg> </a> <a href="" style="text-decoration: none"> <svg
									xmlns="http://www.w3.org/2000/svg" width="2.2vw" height="2vw"
									fill="currentColor" style="color: white; margin-right: 0.5vw"
									class="bi bi-facebook" viewBox="0 0 16 16">
  <path
										d="M16 8.049c0-4.446-3.582-8.05-8-8.05C3.58 0-.002 3.603-.002 8.05c0 4.017 2.926 7.347 6.75 7.951v-5.625h-2.03V8.05H6.75V6.275c0-2.017 1.195-3.131 3.022-3.131.876 0 1.791.157 1.791.157v1.98h-1.009c-.993 0-1.303.621-1.303 1.258v1.51h2.218l-.354 2.326H9.25V16c3.824-.604 6.75-3.934 6.75-7.951" />
</svg>
							</a> <a href="" style="text-decoration: none"> <svg
									xmlns="http://www.w3.org/2000/svg" width="2.2vw" height="2vw"
									fill="currentColor" style="color: white;" class="bi bi-tiktok"
									viewBox="0 0 16 16">
  <path
										d="M9 0h1.98c.144.715.54 1.617 1.235 2.512C12.895 3.389 13.797 4 15 4v2c-1.753 0-3.07-.814-4-1.829V11a5 5 0 1 1-5-5v2a3 3 0 1 0 3 3z" />
</svg>
							</a>



						</div>
					</div>




				</div>
				<div class="col-12 d-block d-md-none d-sm-none  pieDePagina p-1 "
					style="border-top: 1px solid white;">
					<p style="font-size: 2.4vw; margin-top: 1.4vw; color: #d4af37">©
						2024 futboldebarrio.com | Todos los derechos reservados</p>

				</div>
			</div>
		</div>

	</footer>



	<script>
		// Mostrar/Ocultar los filtros al hacer clic en el botón 
		document.getElementById("mostrarFiltrosUsuario").addEventListener(
				"click",
				function() {
					const filtros = document.getElementById("filtrosUsuario");
					const boton = document
							.getElementById("mostrarFiltrosUsuario");

					if (filtros.style.display === "none") {
						filtros.style.display = "flex"; // Mostrar los filtros
						boton.textContent = "Ocultar Filtros"; // Cambiar el texto del botón
					} else {
						filtros.style.display = "none"; // Ocultar los filtros
						boton.textContent = "Mostrar Filtros"; // Cambiar el texto del botón
					}
				});

		document.getElementById("mostrarFiltrosInstalacion").addEventListener(
				"click",
				function() {
					const filtros = document
							.getElementById("filtrosInstalacion");
					const boton = document
							.getElementById("mostrarFiltrosinstalacion");

					if (filtros.style.display === "none") {
						filtros.style.display = "flex"; // Mostrar los filtros
						boton.textContent = "Ocultar Filtros"; // Cambiar el texto del botón
					} else {
						filtros.style.display = "none"; // Ocultar los filtros
						boton.textContent = "Mostrar Filtros"; // Cambiar el texto del botón
					}
				});

		document.getElementById("mostrarFiltrosClub")
				.addEventListener(
						"click",
						function() {
							const filtros = document
									.getElementById("filtrosClub");
							const boton = document
									.getElementById("mostrarFiltrosClub");

							if (filtros.style.display === "none") {
								filtros.style.display = "flex"; // Mostrar los filtros
								boton.textContent = "Ocultar Filtros"; // Cambiar el texto del botón
							} else {
								filtros.style.display = "none"; // Ocultar los filtros
								boton.textContent = "Mostrar Filtros"; // Cambiar el texto del botón
							}
						});

		document.getElementById("mostrarFiltrosArbitros").addEventListener(
				"click",
				function() {
					const filtros = document.getElementById("filtrosArbitros");
					const boton = document
							.getElementById("mostrarFiltrosArbitros");

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
			const valorFiltro = document.getElementById(idFiltro).value
					.toLowerCase();
			const filas = document.querySelectorAll(".tablaDatos tbody tr");

			filas.forEach(function(fila) {
				const celdas = fila.getElementsByTagName("td");
				const textoCelda = celdas[columnaIndex].textContent
						.toLowerCase();
				if (textoCelda.includes(valorFiltro)) {
					fila.style.display = "";
				} else {
					fila.style.display = "none";
				}
			});
		}

		// Agregar evento de filtro para cada campo
		document.getElementById("buscarId").addEventListener("input",
				function() {
					filtrarTabla("buscarId", 0); // Filtro por columna ID (columna 0)
				});

		document.getElementById("buscarNombre").addEventListener("input",
				function() {
					filtrarTabla("buscarNombre", 1); // Filtro por columna Nombre (columna 1)
				});

		document.getElementById("buscarCorreo").addEventListener("input",
				function() {
					filtrarTabla("buscarCorreo", 2); // Filtro por columna Correo (columna 2)
				});

		document.getElementById("buscarIdInstalaciones").addEventListener(
				"input", function() {
					filtrarTabla("buscarIdInstalaciones", 0); // Filtro por columna ID (columna 0)
				});

		document.getElementById("buscarNombreInstalaciones").addEventListener(
				"input", function() {
					filtrarTabla("buscarNombreInstalaciones", 1); // Filtro por columna Nombre (columna 1)
				});

		document.getElementById("buscarCorreoInstalaciones").addEventListener(
				"input", function() {
					filtrarTabla("buscarCorreoInstalaciones", 2); // Filtro por columna Correo (columna 2)
				});

		document.getElementById("buscarIdClub").addEventListener("input",
				function() {
					filtrarTabla("buscarIdClub", 0); // Filtro por columna ID (columna 0)
				});

		document.getElementById("buscarNombreClub").addEventListener("input",
				function() {
					filtrarTabla("buscarNombreClub", 1); // Filtro por columna Nombre (columna 1)
				});

		document.getElementById("buscarCorreoClub").addEventListener("input",
				function() {
					filtrarTabla("buscarCorreoClub", 2); // Filtro por columna Correo (columna 2)
				});

		document.getElementById("buscarIdArbitro").addEventListener("input",
				function() {
					filtrarTabla("buscarIdArbitro", 0); // Filtro por columna ID (columna 0)
				});

		document.getElementById("buscarNombreArbitro").addEventListener(
				"input", function() {
					filtrarTabla("buscarNombreArbitro", 1); // Filtro por columna Nombre (columna 1)
				});

		document.getElementById("buscarCorreoArbitro").addEventListener(
				"input", function() {
					filtrarTabla("buscarCorreoArbitro", 2); // Filtro por columna Correo (columna 2)
				});

		// CODIGO PARA BOTONES ANTERIOR Y SIGUIENTE Y ESCRIBIR EN LA TBODY
		let datos = [];
		for (let i = 1; i <= 50; i++) {
			datos.push({
				id : i,
				nombre : "Jugador " + i,
				correo : "jugador" + i + "@correo.com",
				opciones : "Editar | Eliminar"
			});
		}

		let paginaActual = 1;
		const filasPorPagina = 10;
		const cuerpoTabla = document.getElementById("cuerpoTabla");

		$(document).ready(function () {

			
			function cargarUsuarios() {
				$.ajax({
					url: 'usuario',
					method: 'GET',
					dataType: 'json',
					success: function (data) {
						// console.log(data);
						$('#tablaCuerpoUsuario').empty();

						$.each(data, function (index, usuario) {
							var row = '<tr id="fila-' + usuario.idUsuario + '" style="font-size: 1vw; text-align: center; vertical-align: middle;">'
								+ '<td style="font-weight: bold; border">' + usuario.idUsuario + '</td>'
								+ '<td><img src="data:image/jpeg;base64,' + usuario.imagenUsuario + '" alt="Imagen Usuario" '
								+ 'style="width: 2.5vw; height: 2.5vw; object-fit: cover; border-radius: 10%; display: block; margin: 0 auto;" /></td>'
								+ '<td>' + usuario.nombreCompletoUsuario + '</td>'
								+ '<td>' + usuario.emailUsuario + '</td>'
								+ '<td>'
								+ '<div style="display: flex; justify-content: center; gap: 1vw;">'
								+ '<button class="btnEliminar" data-id="' + usuario.idUsuario + '" '
								+ 'style="border: 1px solid red; height: 1.8vw; width: 1.8vw;">'
								+ '<i class="bi bi-trash3-fill" style="color: #c33214; font-size: 1vw;"></i></button>'
								+ '<button class="btnEditar" data-usuario=\'' + JSON.stringify(usuario) + '\' '
								+ 'style="border: 1px solid orange; height: 1.8vw; width: 1.8vw;">'
								+ '<i class="bi bi-pencil-square" style="font-size: 1vw; color: orange;"></i></button>'
								+ '</div></td></tr>';
							$('#tablaCuerpoUsuario').append(row);
						});
					},
					error: function (xhr, status, error) {
						console.error('Error en la solicitud AJAX:', error);
					}
				});
			}

			// Llamada inicial al cargar
			cargarUsuarios();

			// Delegación de evento para eliminar
			$('#tablaCuerpoUsuario').on('click', '.btnEliminar', function () {
				var idUsuario = $(this).data('id');
				if (confirm("¿Seguro que deseas eliminar este usuario?")) {
					$.ajax({
						url: 'usuario?idUsuario=' + idUsuario,
						method: 'DELETE',
						success: function (response) {
							// console.log(response);
							$('#fila-' + idUsuario).remove();
						},
						error: function (xhr, status, error) {
							console.error('Error al eliminar usuario:', error);
						}
					});
				}
			});

			// Delegación de evento para editar
			$('#tablaCuerpoUsuario').on('click', '.btnEditar', function () {
				var usuario = $(this).data('usuario');

				$('#modalEditarUsuario').modal('show');
				$('#idUsuario').val(usuario.idUsuario);
				$('#nombreCompletoUsuario').val(usuario.nombreCompletoUsuario);
				$('#aliasUsuario').val(usuario.aliasUsuario);
				$('#emailUsuario').val(usuario.emailUsuario);
				$('#fechaNacimientoUsuario').val(usuario.fechaNacimientoUsuario);
				$('#telefonoUsuario').val(usuario.telefonoUsuario);
				$('#passwordUsuario').val(usuario.passwordUsuario);
				$('#rolUsuario').val(usuario.rolUsuario);
				$('#estadoUsuario').val(usuario.estadoUsuario);
				$('#descripcionUsuario').val(usuario.descripcionUsuario);
				$('#imagenUsuarioModal').attr('src', 'data:image/jpeg;base64,' + usuario.imagenUsuario);
			});

			// Manejar formulario de edición
			$('#formEditarUsuario').on('submit', function (e) {
				e.preventDefault();

				var form = $(this)[0];
				var formData = new FormData(form);

				$.ajax({
					url: 'usuario',
					method: 'POST',
					data: formData,
					processData: false,
					contentType: false,
					success: function (response) {
						$('#mensajeModificacion').text(response).css({
							color: response.includes("correctamente") ? "green" : "red",
							display: "block",
							textAlign: "center"
						});

						if (response.includes("correctamente")) {
							setTimeout(function () {
								$('#modalEditarUsuario').modal('hide');
								$('#mensajeModificacion').fadeOut();
								cargarUsuarios(); // 🔁 Recarga tabla al editar correctamente
							}, 1500);
						}
					},
					error: function (xhr, status, error) {
						$('#mensajeModificacion').text('Error al enviar el formulario.').css({
							color: "red",
							display: "block"
						});
					}
				});
			});
		});

		document.addEventListener("DOMContentLoaded", function() {
			// Obtener los botones
			const mostrarInicioBtn = document
					.getElementById('mostrarInicioBtn');
			const mostrarUsuariosBtn = document
					.getElementById('mostrarUsuariosBtn');
			const mostrarInstalacionesBtn = document
					.getElementById('mostrarInstalacionesBtn');
			const mostrarClubesBtn = document
					.getElementById('mostrarClubesBtn');
			const mostrarArbitrosBtn = document
					.getElementById('mostrarArbitrosBtn');

			// Obtener los contenedores
			const inicioContainer = document.getElementById('inicioContainer');
			const userContainer = document.getElementById('usuarioContainer');
			const instalacionesContainer = document
					.getElementById('instalacionesContainer');
			const clubContainer = document.getElementById('clubContainer');
			const arbitrosContainer = document
					.getElementById('arbitrosContainer');

			// Función para mostrar un contenedor y ocultar los demás
			function mostrarContenedor(mostrar) {
				// Ocultar todos los contenedores
				inicioContainer.style.display = "none";
				userContainer.style.display = "none";
				instalacionesContainer.style.display = "none";
				clubContainer.style.display = "none";
				arbitrosContainer.style.display = "none";

				// Mostrar solo el contenedor seleccionado
				mostrar.style.display = "block";
			}

			// Evento para mostrar Inicio (página principal)
			mostrarInicioBtn.addEventListener('click', function(event) {
				event.preventDefault();
				mostrarContenedor(inicioContainer);
			});

			// Evento para mostrar Usuarios
			mostrarUsuariosBtn.addEventListener('click', function(event) {
				event.preventDefault();
				mostrarContenedor(userContainer);
			});

			// Evento para mostrar Instalaciones
			mostrarInstalacionesBtn.addEventListener('click', function(event) {
				event.preventDefault();
				mostrarContenedor(instalacionesContainer);
			});

			// Evento para mostrar Clubes
			mostrarClubesBtn.addEventListener('click', function(event) {
				event.preventDefault();
				mostrarContenedor(clubContainer);
			});

			// Evento para mostrar Arbitros
			mostrarArbitrosBtn.addEventListener('click', function(event) {
				event.preventDefault();
				mostrarContenedor(arbitrosContainer);
			});

			// Mostrar la sección de inicio al cargar la página
			mostrarContenedor(inicioContainer);

			// Obtener todos los enlaces
			const enlaces = document.querySelectorAll("a");

			// Función para resaltar el enlace clicado y restablecer los demás
			function resaltarEnlace(event) {
	const href = this.getAttribute("href");

	// Solo prevenir si el enlace apunta a "#" o es un ancla interna
	if (!href || href === "#" || href.startsWith("#")) {
		event.preventDefault();
	}

	enlaces.forEach(function(enlace) {
		enlace.style.color = "";
	});
	this.style.color = "#d4af37";
}


			// Añadir el evento de clic a cada enlace
			enlaces.forEach(function(enlace) {
				enlace.addEventListener("click", resaltarEnlace);
			});
		});

		mostrarInicioBtn.style.color = "#d4af37"; // Cambiar color de texto a negro
	</script>



</body>
</html>