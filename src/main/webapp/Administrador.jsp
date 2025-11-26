
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

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


								<div class="col-sm-12 col-md-12 ">
									<div class="row">
										<div class="col-md-3 col-sm-3 cabeceraAbajo">
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
										<div class="col-sm-2 col-md-2 cabeceraAbajo">
											<div class="dropdown">
												<button class="btn btn-secondary  " type="button"
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
														<hr class="dropdown-divider"
															style="border-color: #006600;">
													</li>
													<li><a class="dropdown-item" href="logout"
														style="color: white;">Cerrar sesión</a></li>

												</ul>
											</div>
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
									class="col-12 d-flex justify-content-center align-items-center">
									<div class="row">
										<div class="col-2  cabeceraAbajo">
											<a href="#" id="mostrarInicioBtnMobile" class="letraCabeceraAbajo">INICIO</a>

										</div>
										<div class="col-3  cabeceraAbajo">
											<a href="#" id="mostrarUsuariosBtnMobile" class="letraCabeceraAbajo">USUARIOS</a>

										</div>
										<div class=" col-3 cabeceraAbajo">
											<a href="#" id="mostrarInstalacionesBtnMobile" class="letraCabeceraAbajo">INSTALACIONES</a>

										</div>
										<div class="col-2 cabeceraAbajo">
											<a href="#" id="mostrarClubesBtnMobile" class="letraCabeceraAbajo">CLUBES</a>

										</div>
										
								
										
										
										
										
										
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
													style="min-width: 12vw; font-size: 1.8vw; background-color: #003300; border-radius: 5px;">
													<li><a class="dropdown-item active" href="#"
														style="color: white; background-color: #005500;">Idioma
													</a></li>
													<li><a class="dropdown-item" href="#"
														style="color: white;">Ayuda </a></li>
													<li><a class="dropdown-item" href="#"
														style="color: white;">Configuración </a></li>
													<li>
														<hr class="dropdown-divider"
															style="border-color: #006600;">
													</li>
													<li><a class="dropdown-item" href="logout"
														style="color: white;">Cerrar sesión</a></li>

												</ul>
											</div>
										</div>
									</div>
								</div>

								<!-- columna derecha: menú desplegable -->


							</div>
						</div>
					</div>
				</div>



			</div>
		</div>
	</header>

	<main style="background-color: rgba(223, 234, 213, 0.5);">
		<div class="container-fluid mt-2 pt-2">






			<!-- CONTENIDO -->
			<div class="col-md-12 col-sm-12 col-12 contenido ">
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
												 'Premium' ],
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
								<label for="buscarIdUsuario" class="labelFiltrar"><b>-Buscar
										por ID:</b></label> <input type="text" id="buscarIdUsuario"
									class="inputFiltrar" placeholder="Buscar por ID">
							</div>

							<div class="filtroItem">
								<label for="buscarNombreUsuario" class="labelFiltrar"><b>-Buscar
										por Nombre:</b></label> <input type="text" id="buscarNombreUsuario"
									class="inputFiltrar" placeholder="Buscar por Nombre">
							</div>

							<div class="filtroItem">
								<label for="buscarCorreoUsuario" class="labelFiltrar"><b>-Buscar
										por Correo:</b></label> <input type="text" id="buscarCorreoUsuario"
									class="inputFiltrar" placeholder="Buscar por Correo">
							</div>
						</div>

						<div class="tablasAdmin-container">
							<table class="tablaAdmin tablaAdmin--usuarios">
								<thead class="tablaAdmin__head">
									<tr>
										<th>ID</th>
										<th>IMAGEN</th>
										<th>USUARIO</th>
										<th>EMAIL</th>
										<th>OPCIONES</th>
									</tr>
								</thead>
								<tbody id="tablaCuerpoUsuario"></tbody>
							</table>
						</div>
						<div class="paginacion" id="tablaCuerpoUsuario-paginacion"></div>
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
									<button type="button" class="btn-close" data-bs-dismiss="modal"
										aria-label="Cerrar"></button>
								</div>
								<div class="modal-body">
									<form id="formEditarUsuario" method="POST"
										enctype="multipart/form-data" action="usuario">
										<input type="hidden" id="idUsuarioEditar" name="idUsuario">

										<div class="container-fluid">
											<div class="row mb-3">
												<div class="col-md-6 mb-3">
													<label for="nombreCompletoUsuarioEditar"><b>Nombre
															Completo</b></label> <input type="text"
														name="nombreCompletoUsuarioEditar" class="form-control"
														id="nombreCompletoUsuarioEditar"
														style="border: 2px solid #aaa;">
												</div>
												<div class="col-md-6 mb-3">
													<label for="aliasUsuarioEditar"><b>Alias</b></label> <input
														type="text" name="aliasUsuarioEditar" class="form-control"
														id="aliasUsuarioEditar" style="border: 2px solid #aaa;">
												</div>
											</div>

											<div class="row mb-3">
												<div class="col-md-6 mb-3">
													<label for="emailUsuarioEditar"><b>Email</b></label> <input
														type="email" name="emailUsuarioEditar"
														class="form-control" id="emailUsuarioEditar"
														style="border: 2px solid #aaa;">
												</div>
												<div class="col-md-6 mb-3">
													<label for="fechaNacimientoUsuarioEditar"><b>Fecha
															de Nacimiento</b></label> <input type="date"
														name="fechaNacimientoUsuarioEditar" class="form-control"
														id="fechaNacimientoUsuarioEditar"
														style="border: 2px solid #aaa;">
												</div>
											</div>

											<div class="row mb-3">
												<div class="col-md-6 mb-3">
													<label for="telefonoUsuarioEditar"><b>Teléfono</b></label>
													<input type="text" name="telefonoUsuarioEditar"
														class="form-control" id="telefonoUsuarioEditar"
														style="border: 2px solid #aaa;">
												</div>
												<div class="col-md-6 mb-3">
													<label for="rolUsuarioEditar"><b>Rol</b></label> <select
														class="form-select" name="rolUsuarioEditar"
														id="rolUsuarioEditar" style="border: 2px solid #aaa;">
														<option value="Administrador">Administrador</option>
														<option value="Jugador">Jugador</option>

													</select>
												</div>
											</div>

											<div class="row mb-3">
												<div class="col-md-6 mb-3">
													<label for="estadoUsuarioEditar"><b>Estado</b></label> <select
														class="form-select" name="estadoUsuarioEditar"
														id="estadoUsuarioEditar" style="border: 2px solid #aaa;">
														<option value="Activo">Activo</option>
														<option value="Inactivo">Inactivo</option>
													</select>
												</div>
												<div class="col-md-6 mb-3">
													<label for="descripcionUsuarioEditar"><b>Descripción</b></label>
													<input type="text" name="descripcionUsuarioEditar"
														class="form-control" id="descripcionUsuarioEditar"
														style="border: 2px solid #aaa;">
												</div>
											</div>

											<div class="row mb-3">
												<div class="col-md-6 mb-3">
													<label for="imagenUsuarioEditar"><b>Imagen</b></label> <input
														type="file" name="imagenUsuarioEditar"
														class="form-control" id="imagenUsuarioEditar"
														style="border: 2px solid #aaa;">
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
					<div class="col-md-11 col-sm-11 col-12 mx-auto "
						id="instalacionesContainer" style="display: none;">

						<!-- Modal de Edición de Instalación -->

						<div class="modal fade" id="modalEditarInstalacion" tabindex="-1"
							aria-hidden="true">
							<div
								class="modal-dialog modal-lg modal-dialog-centered modal-dialog-scrollable">
								<div class="modal-content">
									<div class="modal-header">
										<h5 class="modal-title w-100 text-center">
											<b>EDITAR INSTALACIÓN</b>
										</h5>
										<button type="button" class="btn-close"
											data-bs-dismiss="modal" aria-label="Cerrar"></button>
									</div>
									<div class="modal-body">
										<form id="formEditarInstalacion" method="POST"
											enctype="multipart/form-data" action="instalacion">
											<input type="hidden" id="idInstalacionEditar"
												name="idInstalacion">
											<div class="container-fluid">
												<div class="row mb-3">
													<div class="col-md-6 mb-3">
														<label for="nombreInstalacionEditar"><b>Nombre</b></label>
														<input type="text" id="nombreInstalacionEditar"
															name="nombreInstalacion" class="form-control" required>
													</div>
													<div class="col-md-6 mb-3">
														<label for="direccionInstalacionEditar"><b>Dirección</b></label>
														<input type="text" id="direccionInstalacionEditar"
															name="direccionInstalacion" class="form-control" required>
													</div>
												</div>

												<div class="row mb-3">
													<div class="col-md-6 mb-3">
														<label for="telefonoInstalacionEditar"><b>Teléfono</b></label>
														<input type="text" id="telefonoInstalacionEditar"
															name="telefonoInstalacion" class="form-control">
													</div>
													<div class="col-md-6 mb-3">
														<label for="emailInstalacionEditar"><b>Email</b></label> <input
															type="email" id="emailInstalacionEditar"
															name="emailInstalacion" class="form-control">
													</div>
												</div>

												<div class="row mb-3">
													<div class="col-md-4 mb-3">
														<label for="tipoCampo1Editar"><b>Tipo Campo 1</b></label>
														<select id="tipoCampo1Editar" name="tipoCampo1"
															class="form-select">
															<option value="">-- Seleccione --</option>
															<option value="Futbol5">FUTBOL 5</option>
															<option value="Futbol7">FUTBOL 7</option>
															<option value="Futbol11">FUTBOL 11</option>
														</select>
													</div>
													<div class="col-md-4 mb-3">
														<label for="tipoCampo2Editar"><b>Tipo Campo 2</b></label>
														<select id="tipoCampo2Editar" name="tipoCampo2"
															class="form-select">
															<option value="">-- Seleccione --</option>
															<option value="Futbol5">FUTBOL 5</option>
															<option value="Futbol7">FUTBOL 7</option>
															<option value="Futbol11">FUTBOL 11</option>
														</select>
													</div>
													<div class="col-md-4 mb-3">
														<label for="tipoCampo3Editar"><b>Tipo Campo 3</b></label>
														<select id="tipoCampo3Editar" name="tipoCampo3"
															class="form-select">
															<option value="">-- Seleccione --</option>
															<option value="Futbol5">FUTBOL 5</option>
															<option value="Futbol7">FUTBOL 7</option>
															<option value="Futbol11">FUTBOL 11</option>
														</select>
													</div>
												</div>

												<div class="row mb-3">
													<div class="col-md-6 mb-3">
														<label for="serviciosInstalacionEditar"><b>Servicios</b></label>
														<textarea id="serviciosInstalacionEditar"
															name="serviciosInstalacion" class="form-control"></textarea>
													</div>
													<div class="col-md-6 mb-3">
														<label for="estadoInstalacionEditar"><b>Estado</b></label>
														<select id="estadoInstalacionEditar"
															name="estadoInstalacion" class="form-select">
															<option value="">-- Seleccione --</option>
															<option value="Activo">ACTIVO</option>
															<option value="Inactivo">INACTIVO</option>
														</select>
													</div>
												</div>

												<div class="row mb-3">
													<div class="col-md-6 mb-3">
														<label for="imagenInstalacionEditar"><b>Imagen
																(opcional)</b></label> <input type="file"
															id="imagenInstalacionEditar" name="imagenInstalacion"
															class="form-control" accept="image/*">
													</div>
												</div>

											</div>
										</form>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-secondary"
											data-bs-dismiss="modal">Cancelar</button>
										<button type="submit" class="btn btn-primary"
											form="formEditarInstalacion">Guardar Cambios</button>
									</div>
								</div>
							</div>
						</div>


						<!-- Botón mostrar filtros -->
						<button id="mostrarFiltrosInstalacion"
							class="mb-3 mr-auto botonFiltrar">Mostrar Filtros</button>

						<!-- Filtros de búsqueda -->
						<div id="filtrosInstalacion" class="filaFiltrar"
							style="display: none;">
							<div class="filtroItem">
								<label for="buscarIdInstalacion" class="labelFiltrar"><b>-Buscar
										por ID:</b></label> <input type="text" id="buscarIdInstalacion"
									class="inputFiltrar" placeholder="Buscar por ID">
							</div>
							<div class="filtroItem">
								<label for="buscarNombreInstalacion" class="labelFiltrar"><b>-Buscar
										por Nombre:</b></label> <input type="text" id="buscarNombreInstalacion"
									class="inputFiltrar" placeholder="Buscar por Nombre">
							</div>
							<div class="filtroItem">
								<label for="buscarCorreoInstalacion" class="labelFiltrar"><b>-Buscar
										por Correo:</b></label> <input type="text" id="buscarCorreoInstalacion"
									class="inputFiltrar" placeholder="Buscar por Correo">
							</div>
						</div>

						<!-- Tabla de instalaciones -->
						<div class="tablasAdmin-container">
							<table class="tablaAdmin tablaAdmin--instalaciones">
								<thead class="tablaAdmin__head">
									<tr>
										<th>ID</th>
										<th>IMAGEN</th>
										<th>INSTALACIÓN</th>
										<th>EMAIL</th>
										<th>OPCIONES</th>
									</tr>
								</thead>
								<tbody id="tablaCuerpoInstalacion"></tbody>
							</table>
						</div>
						<!-- Paginación -->
						<div class="paginacion" id="tablaCuerpoInstalacion-paginacion"></div>


					</div>



					<!-- CLUBES -->
					<div class="col-md-11 col-sm-11 col-12  mx-auto "
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

						<!-- Modal de Edición de Club -->
						<div class="modal fade" id="modalEditarClub" tabindex="-1"
							aria-labelledby="modalEditarClubLabel" aria-hidden="true">
							<div class="modal-dialog modal-lg">
								<div class="modal-content">
									<div class="modal-header">
										<h5 class="modal-title w-100 text-center"
											id="modalEditarClubLabel">
											<b>EDITAR CLUB</b>
										</h5>
										<button type="button" class="btn-close"
											data-bs-dismiss="modal" aria-label="Cerrar"></button>
									</div>
									<div class="modal-body">
										<form id="formEditarClub" method="POST"
											enctype="multipart/form-data" action="club">
											<input type="hidden" id="idClubEditar" name="idClubEditar">

											<div class="container-fluid">
												<div class="row mb-3">
													<div class="col-md-6 mb-3">
														<label for="nombreClubEditar"><b>Nombre del
																Club</b></label> <input type="text" name="nombreClubEditar"
															class="form-control" id="nombreClubEditar"
															style="border: 2px solid #aaa;">
													</div>
													<div class="col-md-6 mb-3">
														<label for="abreviaturaClubEditar"><b>Abreviatura</b></label>
														<input type="text" name="abreviaturaClubEditar"
															class="form-control" id="abreviaturaClubEditar"
															style="border: 2px solid #aaa;">
													</div>
												</div>

												<div class="row mb-3">
													<div class="col-md-6 mb-3">
														<label for="descripcionClubEditar"><b>Descripción</b></label>
														<input type="text" name="descripcionClubEditar"
															class="form-control" id="descripcionClubEditar"
															style="border: 2px solid #aaa;">
													</div>
													<div class="col-md-6 mb-3">
														<label for="emailClubEditar"><b>Email</b></label> <input
															type="email" name="emailClubEditar" class="form-control"
															id="emailClubEditar" style="border: 2px solid #aaa;">
													</div>
												</div>

												<div class="row mb-3">
													<div class="col-md-4 mb-3">
														<label for="telefonoClubEditar"><b>Teléfono</b></label> <input
															type="text" name="telefonoClubEditar"
															class="form-control" id="telefonoClubEditar"
															style="border: 2px solid #aaa;">
													</div>
													<div class="col-md-4 mb-3">
														<label for="fechaCreacionClubEditar"><b>Fecha
																de Creación</b></label> <input type="date"
															name="fechaCreacionClubEditar" class="form-control"
															id="fechaCreacionClubEditar"
															style="border: 2px solid #aaa;">
													</div>
													<div class="col-md-4 mb-3">
														<label for="fechaFundacionClubEditar"><b>Fecha
																de Fundación</b></label> <input type="date"
															name="fechaFundacionClubEditar" class="form-control"
															id="fechaFundacionClubEditar"
															style="border: 2px solid #aaa;">
													</div>
												</div>

												<div class="row mb-3">
													<div class="col-md-6 mb-3">
														<label for="localidadClubEditar"><b>Localidad</b></label>
														<input type="text" name="localidadClubEditar"
															class="form-control" id="localidadClubEditar"
															style="border: 2px solid #aaa;">
													</div>
													<div class="col-md-6 mb-3">
														<label for="paisClubEditar"><b>País</b></label> <input
															type="text" name="paisClubEditar" class="form-control"
															id="paisClubEditar" style="border: 2px solid #aaa;">
													</div>
												</div>

												<div class="row mb-3">

													<div class="col-md-6 mb-3">
														<label for="logoClubEditar"><b>Logo</b></label> <input
															type="file" name="logoClubEditar" class="form-control"
															id="logoClubEditar" style="border: 2px solid #aaa;">
													</div>
												</div>


											</div>

										</form>
									</div>
									<div id="mensajeModificacionClub"
										style="display: none; margin-top: 1vw; font-weight: bold; text-align: center; width: 100%;"></div>
									<div class="modal-footer">
										<button type="button" class="btn btn-secondary"
											data-bs-dismiss="modal">Cancelar</button>
										<button type="submit" class="btn btn-primary"
											form="formEditarClub">Guardar Cambios</button>
									</div>
								</div>
							</div>
						</div>

						<div class="tablasAdmin-container">
							<table class="tablaAdmin tablaAdmin--clubes">
								<thead class="tablaAdmin__head">
									<tr>
										<th>ID</th>
										<th>IMAGEN</th>
										<th>CLUB</th>
										<th>EMAIL</th>
										<th>OPCIONES</th>
									</tr>
								</thead>
								<tbody id="tablaCuerpoClub"></tbody>
							</table>
						</div>

						<div class="paginacion" id="tablaCuerpoClub-paginacion"></div>

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
							class=" col-4 d-block d-md-none d-sm-none g-2 pieDePagina p-2  ">
							<p
								style="text-decoration: underline; font-family: 'Open Sans', sans-serif; font-size: 2vw">ENLACES
								UTILES</p>
							<pre
								style="text-decoration: underline; font-family: 'Open Sans', sans-serif; font-size: 1.6vw">
<a href="" style="color: white">Inicio</a>
<a href="" style="color: white">¿Qué es nuestra web?</a>
<a href="" style="color: white">Próximos eventos</a>
<a href="" style="color: white">Registrarse</a>
<a href="" style="color: white">Términos y Condiciones</a>
<a href="" style="color: white">Política de Privacidad</a> </pre>
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
	document.addEventListener("DOMContentLoaded", () => {
    // ===========================
    // Contenedores y botones
    // ===========================
    const contenedores = {
        inicio: document.getElementById('inicioContainer'),
        usuario: document.getElementById('usuarioContainer'),
        instalaciones: document.getElementById('instalacionesContainer'),
        club: document.getElementById('clubContainer'),
     
    };

    const botonesMostrar = {
        inicio: document.getElementById('mostrarInicioBtn'),
        usuario: document.getElementById('mostrarUsuariosBtn'),
        instalaciones: document.getElementById('mostrarInstalacionesBtn'),
        club: document.getElementById('mostrarClubesBtn'),
 
    };

    const enlaces = document.querySelectorAll("a");

 // Eventos para la versión móvil
    const botonesExtra = [
      "mostrarUsuariosBtnMobile",
      "mostrarInstalacionesBtnMobile",
      "mostrarClubesBtnMobile",
      "mostrarInicioBtnMobile"
    ];

    botonesExtra.forEach(id => {
      const btn = document.getElementById(id);
      if (btn) {
        btn.addEventListener("click", (e) => {
          e.preventDefault();
          if (id.includes("Usuarios")) mostrarContenedor("usuario");
          if (id.includes("Instalaciones")) mostrarContenedor("instalaciones");
          if (id.includes("Clubes")) mostrarContenedor("club");
          if (id.includes("Inicio")) mostrarContenedor("inicio");
        });
      }
    });

    // ===========================
    // Funciones UI
    // ===========================
    function mostrarContenedor(clave) {
        for (const key in contenedores) {
            contenedores[key].style.display = "none";
        }
        contenedores[clave].style.display = "block";
    }

    function toggleFiltros(botonId, filtrosId) {
        const boton = document.getElementById(botonId);
        const filtros = document.getElementById(filtrosId);
        boton.addEventListener("click", () => {
            if (filtros.style.display === "none") {
                filtros.style.display = "flex";
                boton.textContent = "Ocultar Filtros";
            } else {
                filtros.style.display = "none";
                boton.textContent = "Mostrar Filtros";
            }
        });
    }
    function filtrarTabla(idFiltro, columnaIndex) {
        const valorFiltro = document.getElementById(idFiltro).value.toLowerCase().trim(); // Limpiar espacios
        const filas = document.querySelectorAll(".tablaDatos tbody tr");

        filas.forEach(fila => {
        	const textoCelda = fila.getElementsByTagName("td")[columnaIndex].textContent.toLowerCase().trim();
            fila.style.display = textoCelda.includes(valorFiltro) ? "" : "none";
        });
    }


    // ===========================
    // CRUD Usuarios
    // ===========================
   let modalEditarUsuarioBootstrap;

document.addEventListener("DOMContentLoaded", function() {
    cargarUsuarios();
});


function cargarUsuarios() {
    fetch("usuario")
        .then(function(res) { return res.json(); })
        .then(function(data) { mostrarUsuarios(data); })
        .catch(function(err) { console.error("Error cargando usuarios:", err); });
}

function mostrarUsuarios(usuarios) {
	  var tbody = document.getElementById("tablaCuerpoUsuario");
	  tbody.innerHTML = "";

	  usuarios.forEach(function (usu) {
	    var fila = document.createElement("tr");
	    fila.id = "fila-" + usu.idUsuario;

	    var imagen = usu.imagenUsuario
	      ? "data:image/jpeg;base64," + usu.imagenUsuario
	      : "Imagenes/usuarioPorDefecto.jpg";

	    fila.innerHTML =
	      "<td class='tablaAdmin__id'><strong>" + usu.idUsuario + "</strong></td>" +
	      "<td><img src='" + imagen + "' alt='Usuario' class='tablaAdmin__img'></td>" +
	      "<td>" + (usu.nombreCompletoUsuario || "") + "</td>" +
	      "<td>" + (usu.emailUsuario || "") + "</td>" +
	      "<td class='tablaAdmin__opciones'>" +
	      "<div class='tablaAdmin__acciones'>" +
	      "<button class='tablaAdmin__btn tablaAdmin__btn--eliminar' data-id='" + usu.idUsuario + "'>" +
	      "<i class='bi bi-trash3-fill'></i></button>" +
	      "<button class='tablaAdmin__btn tablaAdmin__btn--editar' data-id='" + usu.idUsuario + "'>" +
	      "<i class='bi bi-pencil-square'></i></button>" +
	      "</div>" +
	      "</td>";

	    // Eventos
	    fila.querySelector(".tablaAdmin__btn--eliminar").addEventListener("click", function () {
	      eliminarUsuario(usu.idUsuario);
	    });

	    fila.querySelector(".tablaAdmin__btn--editar").addEventListener("click", function () {
	      abrirModalEditarUsuario(usu);
	    });

	    tbody.appendChild(fila);
	  });
	  paginarTabla("tablaCuerpoUsuario", 8);
	}


// ======================
// Abrir modal de edición
// ======================
function abrirModalEditarUsuario(usu) {
    document.getElementById("idUsuarioEditar").value = usu.idUsuario;
    document.getElementById("nombreCompletoUsuarioEditar").value = usu.nombreCompletoUsuario || "";
    document.getElementById("aliasUsuarioEditar").value = usu.aliasUsuario || "";
    document.getElementById("emailUsuarioEditar").value = usu.emailUsuario || "";
    document.getElementById("fechaNacimientoUsuarioEditar").value = usu.fechaNacimientoUsuario || "";
    document.getElementById("telefonoUsuarioEditar").value = usu.telefonoUsuario || "";
    document.getElementById("rolUsuarioEditar").value = usu.rolUsuario || "";
    document.getElementById("estadoUsuarioEditar").value = usu.estadoUsuario || "";
    document.getElementById("descripcionUsuarioEditar").value = usu.descripcionUsuario || "";

    if (!modalEditarUsuarioBootstrap) {
        modalEditarUsuarioBootstrap = new bootstrap.Modal(document.getElementById("modalEditarUsuario"));
    }
    modalEditarUsuarioBootstrap.show();
}

// ======================
// Enviar formulario de edición
// ======================
document.getElementById("formEditarUsuario").addEventListener("submit", function(e) {
    e.preventDefault();

    var formData = new FormData(e.target);
    formData.append("accion", "modificar");

    fetch("usuario", {
        method: "POST",
        body: formData
    })
    .then(function(res) { return res.text(); })
    .then(function(data) {
        var mensaje = document.getElementById("mensajeModificacion");
        mensaje.textContent = data;
        mensaje.style.display = "block";
        mensaje.style.color = data.includes("correctamente") ? "green" : "red";

        if (data.includes("correctamente")) {
            setTimeout(function() {
                modalEditarUsuarioBootstrap.hide();
                mensaje.style.display = "none";
                cargarUsuarios();
            }, 1500);
        }
    })
    .catch(function(err) {
        var mensaje = document.getElementById("mensajeModificacion");
        mensaje.textContent = "Error al modificar el usuario.";
        mensaje.style.display = "block";
        mensaje.style.color = "red";
    });
});

// ======================
// Eliminar usuario
// ======================
function eliminarUsuario(idUsuario) {
    if (!confirm("¿Seguro que deseas eliminar este usuario?")) return;

    fetch("usuario?idUsuario=" + idUsuario, { method: "DELETE" })
        .then(function(res) {
            if (res.ok) {
                var fila = document.getElementById("fila-" + idUsuario);
                if (fila) fila.remove();
            } else {
                alert("No se pudo eliminar el usuario.");
            }
        })
        .catch(function(err) { console.error("Error eliminando usuario:", err); });
}
//==================== INSTALACIONES ====================


var modalEditarInstalacionBootstrap;

// Cargar instalaciones
function cargarInstalaciones() {
    fetch("instalacion")
    .then(function(res) { return res.json(); })
    .then(function(data) { mostrarInstalaciones(data); })
    .catch(function(err) { console.error("Error al cargar instalaciones:", err); });
}

// Mostrar instalaciones en tabla
function mostrarInstalaciones(instalaciones) {
  var tbody = document.getElementById("tablaCuerpoInstalacion");
  tbody.innerHTML = "";

  instalaciones.forEach(function (inst) {
    var fila = document.createElement("tr");
    fila.id = "fila-" + inst.idInstalacion;

    var imagen = inst.imagenInstalacion
      ? "data:image/jpeg;base64," + inst.imagenInstalacion
      : "Imagenes/usuarioPorDefecto.jpg";

    fila.innerHTML =
      "<td class='tablaAdmin__id'><strong>" + inst.idInstalacion + "</strong></td>" +
      "<td><img src='" + imagen + "' alt='Instalación' class='tablaAdmin__img'></td>" +
      "<td>" + (inst.nombreInstalacion || "") + "</td>" +
      "<td>" + (inst.emailInstalacion || "") + "</td>" +
      "<td class='tablaAdmin__opciones'>" +
      "<div class='tablaAdmin__acciones'>" +
      "<button class='tablaAdmin__btn tablaAdmin__btn--eliminar' data-id='" + inst.idInstalacion + "'>" +
      "<i class='bi bi-trash3-fill'></i></button>" +
      "<button class='tablaAdmin__btn tablaAdmin__btn--editar' data-id='" + inst.idInstalacion + "'>" +
      "<i class='bi bi-pencil-square'></i></button>" +
      "</div>" +
      "</td>";

    // Eventos
    fila.querySelector(".tablaAdmin__btn--eliminar").addEventListener("click", function () {
      eliminarInstalacion(inst.idInstalacion);
    });

    fila.querySelector(".tablaAdmin__btn--editar").addEventListener("click", function () {
      abrirModalEditarInstalacion(inst);
    });

    tbody.appendChild(fila);
  });
  paginarTabla("tablaCuerpoInstalacion", 8);
}

function mapearModalidad(valorDB) {
    if (!valorDB) return "";
    switch(valorDB) {
        case "Futbol5": return "Futbol5"; // si tu option tiene typo
        case "Futbol7": return "Futbol7";
        case "Futbol11": return "Futbol11";
        default: return "";
    }
}
// Abrir modal con datos
function abrirModalEditarInstalacion(inst) {
    document.getElementById("idInstalacionEditar").value = inst.idInstalacion;
    document.getElementById("nombreInstalacionEditar").value = inst.nombreInstalacion || "";
    document.getElementById("direccionInstalacionEditar").value = inst.direccionInstalacion || "";
    document.getElementById("telefonoInstalacionEditar").value = inst.telefonoInstalacion || "";
    document.getElementById("emailInstalacionEditar").value = inst.emailInstalacion || "";
    document.getElementById("serviciosInstalacionEditar").value = inst.serviciosInstalacion || "";
    document.getElementById("estadoInstalacionEditar").value = inst.estadoInstalacion || "";

    document.getElementById("tipoCampo1Editar").value = mapearModalidad(inst.tipoCampo1);
    document.getElementById("tipoCampo2Editar").value = mapearModalidad(inst.tipoCampo2);
    document.getElementById("tipoCampo3Editar").value = mapearModalidad(inst.tipoCampo3);

    if (!modalEditarInstalacionBootstrap) {
        modalEditarInstalacionBootstrap = new bootstrap.Modal(document.getElementById("modalEditarInstalacion"));
    }
    modalEditarInstalacionBootstrap.show();
}

// Guardar cambios
document.getElementById("formEditarInstalacion").addEventListener("submit", function(e) {
    e.preventDefault();

    var formData = new FormData(e.target);
    formData.append("accion", "modificar");

    fetch("instalacion", {
        method: "POST",
        body: formData
    })
    .then(function(res) { return res.text(); })
    .then(function(data) {
        alert(data);
        modalEditarInstalacionBootstrap.hide();
        cargarInstalaciones();
    })
    .catch(function(err) { console.error("Error al modificar instalación:", err); });
});

// Eliminar instalación
function eliminarInstalacion(id) {
    if (!confirm("¿Seguro que deseas eliminar esta instalación?")) return;

    fetch("instalacion?idInstalacion=" + id, { method: "DELETE" })
    .then(function(res) {
        if (res.ok) {
            var fila = document.getElementById("fila-" + id);
            if (fila) fila.remove();
        } else {
            alert("No se pudo eliminar la instalación.");
        }
    })
    .catch(function(err) { console.error("Error eliminando instalación:", err); });
}

// ===========================
// Delegación de eventos
// ===========================
document.getElementById("tablaCuerpoUsuario").addEventListener("click", (e) => {
    const btn = e.target.closest(".btnEliminarInstalacion, .btnEditarInstalacion");
    if (!btn) return;

    if (btn.classList.contains("btnEliminarInstalacion")) {
        eliminarUsuario(btn.dataset.id);
    }

    if (btn.classList.contains("btnEditarInstalacion")) {
        const usuario = JSON.parse(btn.dataset.usuario);
        document.getElementById("modalEditarUsuario").classList.add("show");
        document.getElementById("idUsuario").value = usuario.idUsuario;
        document.getElementById("nombreCompletoUsuario").value = usuario.nombreCompletoUsuario;
        document.getElementById("aliasUsuario").value = usuario.aliasUsuario;
        document.getElementById("emailUsuario").value = usuario.emailUsuario;
        document.getElementById("fechaNacimientoUsuario").value = usuario.fechaNacimientoUsuario;
        document.getElementById("telefonoUsuario").value = usuario.telefonoUsuario;
        document.getElementById("passwordUsuario").value = usuario.passwordUsuario;
        document.getElementById("rolUsuario").value = usuario.rolUsuario;
        document.getElementById("estadoUsuario").value = usuario.estadoUsuario;
        document.getElementById("descripcionUsuario").value = usuario.descripcionUsuario;
        document.getElementById("imagenUsuarioModal").src = "data:image/jpeg;base64," + usuario.imagenUsuario;
    }
});

// Inicializar carga al inicio
document.addEventListener("DOMContentLoaded", function() {
    cargarInstalaciones();
});

let modalEditarClubBootstrap;

// Mostrar/ocultar filtros
function toggleFiltros(botonId, filtrosId) {
    const boton = document.getElementById(botonId);
    const filtros = document.getElementById(filtrosId);
    boton.addEventListener("click", () => {
        if (filtros.style.display === "none") {
            filtros.style.display = "flex";
            boton.textContent = "Ocultar Filtros";
        } else {
            filtros.style.display = "none";
            boton.textContent = "Mostrar Filtros";
        }
    });
}


// Cargar clubes
function cargarClubes() {
    fetch("club")
        .then(function(res) { return res.json(); })
        .then(function(data) { mostrarClubes(data); })
        .catch(function(err) { console.error("Error al cargar clubes:", err); });
}

// Mostrar clubes en tabla
function mostrarClubes(clubes) {
  var tbody = document.getElementById("tablaCuerpoClub");
  tbody.innerHTML = "";

  clubes.forEach(function (club) {
    var fila = document.createElement("tr");
    fila.id = "fila-" + club.idClub;

    var imagen = club.logoClub
      ? "data:image/jpeg;base64," + club.logoClub
      : "Imagenes/usuarioPorDefecto.jpg"; // Imagen por defecto

    fila.innerHTML =
      "<td class='tablaAdmin__id'><strong>" + club.idClub + "</strong></td>" +
      "<td><img src='" + imagen + "' alt='Club' class='tablaAdmin__img'></td>" +
      "<td>" + (club.nombreClub || "") + "</td>" +
      "<td>" + (club.emailClub || "") + "</td>" +
      "<td class='tablaAdmin__opciones'>" +
      "<div class='tablaAdmin__acciones'>" +
      "<button class='tablaAdmin__btn tablaAdmin__btn--eliminar' data-id='" + club.idClub + "'>" +
      "<i class='bi bi-trash3-fill'></i></button>" +
      "<button class='tablaAdmin__btn tablaAdmin__btn--editar' data-club='" + encodeURIComponent(JSON.stringify(club)) + "'>" +
      "<i class='bi bi-pencil-square'></i></button>" +
      "</div>" +
      "</td>";

    // Eventos
    fila.querySelector(".tablaAdmin__btn--eliminar").addEventListener("click", function () {
      eliminarClub(club.idClub);
    });

    fila.querySelector(".tablaAdmin__btn--editar").addEventListener("click", function (e) {
      var clubData = JSON.parse(decodeURIComponent(e.currentTarget.dataset.club));
      abrirModalEditarClub(clubData);
    });

    tbody.appendChild(fila);
  });
  paginarTabla("tablaCuerpoClub", 8);
}


// Abrir modal de edición
function abrirModalEditarClub(club) {
	function formatearFechaParaInput(dateTimeStr) {
	    if (!dateTimeStr) return "";
	    return dateTimeStr.split("T")[0]; // toma solo la parte YYYY-MM-DD
	}
    document.getElementById("idClubEditar").value = club.idClub || "";
    document.getElementById("nombreClubEditar").value = club.nombreClub || "";
    document.getElementById("abreviaturaClubEditar").value = club.abreviaturaClub || "";
    document.getElementById("descripcionClubEditar").value = club.descripcionClub || "";
    document.getElementById("emailClubEditar").value = club.emailClub || "";
    document.getElementById("telefonoClubEditar").value = club.telefonoClub || "";
    document.getElementById("fechaCreacionClubEditar").value = formatearFechaParaInput(club.fechaCreacionClub);
    document.getElementById("fechaFundacionClubEditar").value = formatearFechaParaInput(club.fechaFundacionClub);
    document.getElementById("localidadClubEditar").value = club.localidadClub || "";
    document.getElementById("paisClubEditar").value = club.paisClub || "";

    if (!modalEditarClubBootstrap) {
        modalEditarClubBootstrap = new bootstrap.Modal(document.getElementById("modalEditarClub"));
    }
    modalEditarClubBootstrap.show();
}

// Enviar formulario de edición
document.getElementById("formEditarClub").addEventListener("submit", function(e) {
    e.preventDefault();

    // Crear FormData desde el formulario
    var formData = new FormData(e.target);
    formData.append("accion", "modificar"); // importante para tu servlet

    fetch("club", {
        method: "POST",
        body: formData // NO JSON, porque tu servlet no lo lee
    })
    .then(res => res.text())
    .then(data => {
        var mensaje = document.getElementById("mensajeModificacionClub");
        mensaje.textContent = data;
        mensaje.style.display = "block";
        mensaje.style.color = data.includes("correctamente") ? "green" : "red";

        if (data.includes("correctamente")) {
            setTimeout(() => {
                modalEditarClubBootstrap.hide();
                mensaje.style.display = "none";
                cargarClubes(); // refresca la tabla
            }, 1500);
        }
    })
    .catch(err => {
        var mensaje = document.getElementById("mensajeModificacionClub");
        mensaje.textContent = "Error al modificar el club.";
        mensaje.style.display = "block";
        mensaje.style.color = "red";
        console.error(err);
    });
});



// Eliminar club
function eliminarClub(idClub) {
    if (!confirm("¿Seguro que deseas eliminar este club?")) return;

    fetch("club?idClub=" + idClub, { method: "DELETE" })
        .then(function(res) {
            if (res.ok) {
                var fila = document.getElementById("fila-" + idClub);
                if (fila) fila.remove();
            } else {
                alert("No se pudo eliminar el club.");
            }
        })
        .catch(function(err) { console.error("Error eliminando club:", err); });
}




  

    // ===========================
    // Mostrar secciones
    // ===========================
    for (const key in botonesMostrar) {
        botonesMostrar[key].addEventListener("click", (e) => {
            e.preventDefault();
            mostrarContenedor(key);
        });
    }
    mostrarContenedor("inicio"); // mostrar inicio al cargar

    // ===========================
    // Resaltar enlaces
    // ===========================
    enlaces.forEach(enlace => {
        enlace.addEventListener("click", function(event) {
            const href = this.getAttribute("href");
            if (!href || href === "#" || href.startsWith("#")) event.preventDefault();
            enlaces.forEach(e => e.style.color = "");
            this.style.color = "#d4af37";
        });
    });

    // ===========================
    // Filtros
    // ===========================
    toggleFiltros("mostrarFiltrosUsuario", "filtrosUsuario");
    toggleFiltros("mostrarFiltrosInstalacion", "filtrosInstalacion");
    toggleFiltros("mostrarFiltrosClub", "filtrosClub");
  
    
    function filtrarUsuarios() {
        const idFiltro = document.getElementById("buscarIdUsuario").value.toLowerCase().trim();
        const nombreFiltro = document.getElementById("buscarNombreUsuario").value.toLowerCase().trim();
        const correoFiltro = document.getElementById("buscarCorreoUsuario").value.toLowerCase().trim();

        const filas = document.querySelectorAll("#tablaCuerpoUsuario tr");

        filas.forEach(fila => {
            const idTexto = fila.children[0].textContent.toLowerCase().trim();
            const nombreTexto = fila.children[2].textContent.toLowerCase().trim();
            const correoTexto = fila.children[3].textContent.toLowerCase().trim();

            const coincide =
                idTexto.includes(idFiltro) &&
                nombreTexto.includes(nombreFiltro) &&
                correoTexto.includes(correoFiltro);

            fila.style.display = coincide ? "" : "none";
        });
        paginarTabla("tablaCuerpoUsuario", 8);
    }

    ["buscarIdUsuario", "buscarNombreUsuario", "buscarCorreoUsuario"].forEach(id => {
        const input = document.getElementById(id);
        if (input) input.addEventListener("input", filtrarUsuarios);
    });


    function filtrarClubes() {
        const idFiltro = document.getElementById("buscarIdClub").value.toLowerCase().trim();
        const nombreFiltro = document.getElementById("buscarNombreClub").value.toLowerCase().trim();
        const correoFiltro = document.getElementById("buscarCorreoClub").value.toLowerCase().trim();

        const filas = document.querySelectorAll("#tablaCuerpoClub tr");

        filas.forEach(fila => {
            const idTexto = fila.children[0].textContent.toLowerCase().trim();
            const nombreTexto = fila.children[2].textContent.toLowerCase().trim();
            const correoTexto = fila.children[3].textContent.toLowerCase().trim();

            const coincide =
                idTexto.includes(idFiltro) &&
                nombreTexto.includes(nombreFiltro) &&
                correoTexto.includes(correoFiltro);

            fila.style.display = coincide ? "" : "none";
        });
        paginarTabla("tablaCuerpoClub", 8);
    }

    ["buscarIdClub", "buscarNombreClub", "buscarCorreoClub"].forEach(id => {
        const input = document.getElementById(id);
        if (input) input.addEventListener("input", filtrarClubes);
    });
    
    
    
    function filtrarInstalaciones() {
        const idFiltro = document.getElementById("buscarIdInstalacion").value.toLowerCase().trim();
        const nombreFiltro = document.getElementById("buscarNombreInstalacion").value.toLowerCase().trim();
        const correoFiltro = document.getElementById("buscarCorreoInstalacion").value.toLowerCase().trim();

        const filas = document.querySelectorAll("#tablaCuerpoInstalacion tr");

        filas.forEach(fila => {
            const idTexto = fila.children[0].textContent.toLowerCase().trim();
            const nombreTexto = fila.children[2].textContent.toLowerCase().trim();
            const correoTexto = fila.children[3].textContent.toLowerCase().trim();

            const coincide =
                idTexto.includes(idFiltro) &&
                nombreTexto.includes(nombreFiltro) &&
                correoTexto.includes(correoFiltro);

            fila.style.display = coincide ? "" : "none";
        });
        paginarTabla("tablaCuerpoInstalacion", 8);
    }

    ["buscarIdInstalacion", "buscarNombreInstalacion", "buscarCorreoInstalacion"].forEach(id => {
        const input = document.getElementById(id);
        if (input) input.addEventListener("input", filtrarInstalaciones);
    });
    
 // ===========================================
 // PAGINACIÓN 
 // ===========================================
 function paginarTabla(tablaBodyId, filasPorPagina) {
   if (filasPorPagina === undefined) filasPorPagina = 8;

   var tbody = document.getElementById(tablaBodyId);
   if (!tbody) return;

   var todasFilas = Array.prototype.slice.call(tbody.querySelectorAll("tr"));
   var filasVisibles = todasFilas.filter(function (f) {
     return f.style.display !== "none";
   });

   var totalPaginas = Math.ceil(filasVisibles.length / filasPorPagina);
   var paginaActual = 1;

   var pagDiv = document.getElementById(tablaBodyId + "-paginacion");
   if (!pagDiv) return;

   function mostrarPagina(pagina) {
     var inicio = (pagina - 1) * filasPorPagina;
     var fin = inicio + filasPorPagina;

     filasVisibles.forEach(function (fila, i) {
       fila.style.display = (i >= inicio && i < fin) ? "" : "none";
     });

     pagDiv.innerHTML = "";

     var btnAnterior = document.createElement("button");
     btnAnterior.textContent = "< Anterior";
     btnAnterior.className = "botonPaginacion";
     btnAnterior.disabled = (pagina === 1);
     btnAnterior.addEventListener("click", function () {
       paginaActual--;
       mostrarPagina(paginaActual);
     });

     var spanInfo = document.createElement("span");
     spanInfo.className = "infoPaginacion";
     spanInfo.textContent = " Página " + pagina + " de " + totalPaginas + " ";

     var btnSiguiente = document.createElement("button");
     btnSiguiente.textContent = "Siguiente >";
     btnSiguiente.className = "botonPaginacion";
     btnSiguiente.disabled = (pagina === totalPaginas);
     btnSiguiente.addEventListener("click", function () {
       paginaActual++;
       mostrarPagina(paginaActual);
     });

     pagDiv.appendChild(btnAnterior);
     pagDiv.appendChild(spanInfo);
     pagDiv.appendChild(btnSiguiente);
   }

   if (totalPaginas > 1) {
     mostrarPagina(paginaActual);
   } else {
     filasVisibles.forEach(function (fila) {
       fila.style.display = "";
     });
     pagDiv.innerHTML = ""; // sin paginación si hay pocas filas
   }
 }

 



    
    // ===========================
    // Carga inicial
    // ===========================
    cargarUsuarios();
    cargarInstalaciones();
    cargarClubes();
});
	
	</script>


</body>
</html>