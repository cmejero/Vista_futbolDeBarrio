
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
Long instalacionId = (Long) session.getAttribute("idInstalacion");
String nombreInstalacion = (String) session.getAttribute("nombreInstalacion");
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Estilos CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/Css/Estilo.css">

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
	
	 <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
	

<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"
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

<title>INSTALACION</title>
</head>
<body>
	<header class="fixed-top">
		<!-- Contenedor principal de -->
		<div class="container-fluid ">
			<div class="row ">
				<div class="col-sm-12 col-md-12  d-sm-block d-md-block d-none">
					<div class="row">

						<!-- columna logo -->
						<div class="col-sm-1 col-md-1 logo"
							style="background-color: white; border-top: 2px solid black; border-left: 1px solid black">
							<img
								src="${pageContext.request.contextPath}/Imagenes/LOGOWEB.PNG"></img>
						</div>

						<!-- Columna derecha que se divide en 2 filas -->
						<div class="col-sm-11 col-md-11">
							<div class="row ">
								<!-- fila arriba -->
								<div class="col-sm-12 col-md-12" style="border: solid 2px black">
									<div class="row">
										<div class="col-sm-3 col-md-3 cabeceraArriba p-2 "></div>
										<div class="col-sm-4 col-md-4 cabeceraArriba "
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
											futboldebarriosevilla@gmail.com
										</div>
										<div class="col-sm-1 col-md-1 cabeceraArriba"></div>
										<div class="col-sm-2 col-md-2 cabeceraArriba "
											style="justify-content: left;">
											<a href="">
												<button type="button" class="botonCabeceraContactar"
													onclick="abrirGmail()">CONTACTAR</button>
											</a>
										</div>
										<div class="col-sm-2 col-md-2 cabeceraArriba">
											<a href="https://www.instagram.com/futboldebarriosevilla/">
												<svg xmlns="http://www.w3.org/2000/svg" width="1.4vw"
													height="1.2vw" fill="currentColor"
													style="margin-right: 3px; color: white;" bi bi-instagram
													viewBox="0 0 16 16">
	  <path
														d="M8 0C5.829 0 5.556.01 4.703.048 3.85.088 3.269.222 2.76.42a3.9 3.9 0 0 0-1.417.923A3.9 3.9 0 0 0 .42 2.76C.222 3.268.087 3.85.048 4.7.01 5.555 0 5.827 0 8.001c0 2.172.01 2.444.048 3.297.04.852.174 1.433.372 1.942.205.526.478.972.923 1.417.444.445.89.719 1.416.923.51.198 1.09.333 1.942.372C5.555 15.99 5.827 16 8 16s2.444-.01 3.298-.048c.851-.04 1.434-.174 1.943-.372a3.9 3.9 0 0 0 1.416-.923c.445-.445.718-.891.923-1.417.197-.509.332-1.09.372-1.942C15.99 10.445 16 10.173 16 8s-.01-2.445-.048-3.299c-.04-.851-.175-1.433-.372-1.941a3.9 3.9 0 0 0-.923-1.417A3.9 3.9 0 0 0 13.24.42c-.51-.198-1.092-.333-1.943-.372C10.443.01 10.172 0 7.998 0zm-.717 1.442h.718c2.136 0 2.389.007 3.232.046.78.035 1.204.166 1.486.275.373.145.64.319.92.599s.453.546.598.92c.11.281.24.705.275 1.485.039.843.047 1.096.047 3.231s-.008 2.389-.047 3.232c-.035.78-.166 1.203-.275 1.485a2.5 2.5 0 0 1-.599.919c-.28.28-.546.453-.92.598-.28.11-.704.24-1.485.276-.843.038-1.096.047-3.232.047s-2.39-.009-3.233-.047c-.78-.036-1.203-.166-1.485-.276a2.5 2.5 0 0 1-.92-.598 2.5 2.5 0 0 1-.6-.92c-.109-.281-.24-.705-.275-1.485-.038-.843-.046-1.096-.046-3.233s.008-2.388.046-3.231c.036-.78.166-1.204.276-1.486.145-.373.319-.64.599-.92s.546-.453.92-.598c.282-.11.705-.24 1.485-.276.738-.034 1.024-.044 2.515-.045zm4.988 1.328a.96.96 0 1 0 0 1.92.96.96 0 0 0 0-1.92m-4.27 1.122a4.109 4.109 0 1 0 0 8.217 4.109 4.109 0 0 0 0-8.217m0 1.441a2.667 2.667 0 1 1 0 5.334 2.667 2.667 0 0 1 0-5.334" />
	</svg>
											</a> <a href="https://x.com/FDB_Sevilla"> <svg
													xmlns="http://www.w3.org/2000/svg" width="1.4vw"
													height="1.2vw" fill="currentColor"
													style="margin-right: 3px; color: white;"
													class="bi bi-twitter-x" viewBox="0 0 16 16">
	  <path
														d="M12.6.75h2.454l-5.36 6.142L16 15.25h-4.937l-3.867-5.07-4.425 5.07H.316l5.733-6.57L0 .75h5.063l3.495 4.633L12.601.75Zm-.86 13.028h1.36L4.323 2.145H2.865z" />
	</svg>
											</a> <a
												href="https://www.youtube.com/channel/UCfzHaUblCQl7lzp4CHXR9ug">
												<svg xmlns="http://www.w3.org/2000/svg" width="1.4vw"
													height="1.4vw" fill="currentColor"
													style="margin-right: 3px; color: white;"
													class="bi bi-youtube" viewBox="0 0 16 16">
	  <path
														d="M8.051 1.999h.089c.822.003 4.987.033 6.11.335a2.01 2.01 0 0 1 1.415 1.42c.101.38.172.883.22 1.402l.01.104.022.26.008.104c.065.914.073 1.77.074 1.957v.075c-.001.194-.01 1.108-.082 2.06l-.008.105-.009.104c-.05.572-.124 1.14-.235 1.558a2.01 2.01 0 0 1-1.415 1.42c-1.16.312-5.569.334-6.18.335h-.142c-.309 0-1.587-.006-2.927-.052l-.17-.006-.087-.004-.171-.007-.171-.007c-1.11-.049-2.167-.128-2.654-.26a2.01 2.01 0 0 1-1.415-1.419c-.111-.417-.185-.986-.235-1.558L.09 9.82l-.008-.104A31 31 0 0 1 0 7.68v-.123c.002-.215.01-.958.064-1.778l.007-.103.003-.052.008-.104.022-.26.01-.104c.048-.519.119-1.023.22-1.402a2.01 2.01 0 0 1 1.415-1.42c.487-.13 1.544-.21 2.654-.26l.17-.007.172-.006.086-.003.171-.007A100 100 0 0 1 7.858 2zM6.4 5.209v4.818l4.157-2.408z" />																						
											</a> <a href="https://www.tiktok.com/@fdb_sevilla"> <svg
													xmlns="http://www.w3.org/2000/svg" width="1.4vw"
													height="1.2vw" fill="currentColor"
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

										<div class="col-sm-2 col-md-2 cabeceraMedio"></div>

										<div class="col-sm-4 col-md-4 cabeceraMedio"
											style="text-decoration: underline;">
											<a href="" class="letraCabeceraMedio"
												id="nombreUsuarioCabecera"> BIENVENIDO: <%=nombreInstalacion%>
											</a>
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
											<a href="${pageContext.request.contextPath}/instalacion" class="letraCabeceraAbajo">INICIO</a>
										</div>
										<div class="col-sm-4 col-md-4 cabeceraAbajo ">
											<a href="#" class="letraCabeceraAbajo seccion-bloqueada">
												RESERVAS <span class="tooltip-text">Sección en
													desarrollo</span>
											</a>

										</div>
										<div class="col-sm-3 col-md-3 cabeceraAbajo ">
											<a href="${pageContext.request.contextPath}/instalacion/eventos" class="letraCabeceraAbajo" style="color: #d4af37;">EVENTOS</a>
										</div>
										<div class="col-sm-1 col-md-1 cabeceraAbajo"></div>

									</div>
								</div>

								<!-- columna derecha -->
								<div class="col-sm-7 col-md-7 ">

									<div class="row">
										<div class="col-sm-1 col-md-1 cabeceraAbajo "></div>
										<div class="col-sm-2 col-md-2 cabeceraAbajo ">
											<a href="#" class="letraCabeceraAbajo seccion-bloqueada">
												FINANZAS <span class="tooltip-text">Sección en
													desarrollo</span>
											</a>
										</div>
										<div class="col-sm-4 col-md-4 cabeceraAbajo ">
											<a href="#" class="letraCabeceraAbajo seccion-bloqueada">
												ESTADISTICAS <span class="tooltip-text">Sección en
													desarrollo</span>
											</a>
										</div>
										<div class="col-sm-3 col-md-3 cabeceraAbajo ">
											<a href="#" class="letraCabeceraAbajo seccion-bloqueada">
												NOTIFICACIONES <span class="tooltip-text">Sección en
													desarrollo</span>
											</a>
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
													<li><a class="dropdown-item seccion-bloqueada"
														href="#">Idioma<span
															class="tooltip-text">Sección en desarrollo</span>
													</a></li>
													<li><a class="dropdown-item seccion-bloqueada"
														href="#">Ayuda <span
															class="tooltip-text">Sección en desarrollo</span></a></li>
													<li><a class="dropdown-item seccion-bloqueada"
														href="#">Configuración <span
															class="tooltip-text">Sección en desarrollo</span>
													</a></li>
													<li>
														<hr class="dropdown-divider"
															style="border-color: #006600;">
													</li>
													<li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout"
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
							<img src="${pageContext.request.contextPath}/Imagenes/LOGOWEB.PNG"></img>
						</div>

						<!-- Columna derecha que se divide en 2 filas -->
						<div class="d-sm-none d-md-none col-10 d-block ">
							<div class="row ">

								<!-- fila arriba -->
								<div class="d-sm-none d-md-none col-12 d-block"
									style="border: 2px solid black; border-bottom: none; border-left: none; border-top: 1px solid black;">
									<div class="row">
										<div
											class="d-sm-none d-md-none col-9 d-block cabeceraMedioTituloX">
											<span style="color: #d4af37; margin-right: 1.5vw;"> /
											</span> FUTBOL DE BARRIO <span
												style="color: #c0c0c0; margin-left: 7px;"> /</span>
										</div>
										<div
											class="d-sm-none d-md-none col-3 d-block cabeceraMedio  d-flex justify-content-center align-items-center">

										</div>

									</div>
								</div>
							</div>


							<!-- fila de abajo -->
							<div class="row p-1"
								style="border-bottom: solid 2.4px black; border-top: solid 2px black; border-right: solid 2px black; border-left: none; background-color: #004000; box-shadow: 0px 4px 8px -4px rgba(0, 0, 0, 0.45);">

								<!-- columna izquierda: INICIO -->
								<div
									class="col-3 d-flex justify-content-start align-items-center ps-4 ">
									<a href="${pageContext.request.contextPath}/instalacion" class="letraCabeceraAbajo "
										style="text-decoration: none; font-size: 2.5vw;">INICIO</a>
								</div>

								<!-- columna medio: -->

								<div
									class="col-3 d-flex justify-content-start align-items-center ps-4 ">
									<a href="${pageContext.request.contextPath}/instalacion/eventos" class="letraCabeceraAbajo "
										style="text-decoration: none; font-size: 2.5vw; color: #d4af37; ">EVENTOS</a>
								</div>


								<div
									class="col-4 d-flex justify-content-start align-items-center ps-4 ">
									<a href="Instalacion.jsp"
										class="letraCabeceraAbajo seccion-bloqueada "
										style="text-decoration: none; font-size: 2.5vw;">ESTADISTICAS
										<span class="tooltip-text">Sección en desarrollo</span>
									</a>
								</div>

								<!-- columna derecha: menú desplegable -->
								<div
									class="col-2 d-flex justify-content-end align-items-center pe-4">
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


											<li><a class="dropdown-item seccion-bloqueada"
												href="Instalacion.jsp"">Reservas <span
													class="tooltip-text">Sección en desarrollo</span></a></li>
											<li><a class="dropdown-item  seccion-bloqueada"
												href="Instalacion.jsp"">Finanzas<span
													class="tooltip-text">Sección en desarrollo</span>
											</a></li>
											<li><a class="dropdown-item seccion-bloqueada"
												href="Instalacion.jsp"">Notificaciones <span
													class="tooltip-text">Sección en desarrollo</span></a></li>


											<li>
												<hr class="dropdown-divider" style="border-color: #006600;">
											</li>
											<li><a class="dropdown-item seccion-bloqueada" href="#">Idioma
													<span class="tooltip-text">Sección en desarrollo</span>
											</a></li>
											<li><a class="dropdown-item seccion-bloqueada" href="#">Ayuda
													<span class="tooltip-text">Sección en desarrollo</span>
											</a></li>
											<li><a class="dropdown-item seccion-bloqueada" href="#">Configuración
													<span class="tooltip-text">Sección en desarrollo</span>
											</a></li>

											<li>
												<hr class="dropdown-divider" style="border-color: #006600;">
											</li>
											<li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout"
												style="color: white;">Cerrar sesión</a></li>
											<li>
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
		<div class="container-fluid mt-3 pt-1">
			<div class="row">
				<!-- CONTENIDO -->
				<div class="col-md-12 col-sm-12 col-12">


					<!-- CONTENEDOR TORNEOS -->
					<div class="container-fluid " id="marcadorContainer">
						<div class="row">
							<div class="col-md-12 col-sm-12 col-12 mx-auto">
								<div class="row">

									<div class="col-md-2 col-sm-2 col-2 mx-auto"></div>
									<div class="col-md-3 col-sm-3 col-3 mx-auto"
										style="margin-bottom: 16vh; margin-top: 4vh; display: flex; justify-content: flex-end; align-items: center;">

										<button id="botonLiga"
											class="botonMarcadores p-4 en-desarrollo">
											<img class="imagenMarcadores" src="${pageContext.request.contextPath}/Imagenes/Liga.JPG"
												alt="Imagen la liga"> LIGA <span
												class="badge-desarrollo">En desarrollo</span>
										</button>

									</div>


									<!-- Espacio vacío entre los dos botones -->
									<div class="col-md-1 col-sm-1 col-1 mx-auto "></div>

									<!-- Columna para el botón de JUGADORES alineado a la derecha -->
									<div class="col-md-3 col-sm-3 col-3"
										style="margin-bottom: 16vh; margin-top: 4vh; display: flex; justify-content: flex-start; align-items: center;">
										<button class="botonMarcadores p-4" id="botonTorneo">
											<img class="imagenMarcadores" src="${pageContext.request.contextPath}/Imagenes/copa.JPG"
												alt="Imagen torneo"> TORNEO
										</button>
									</div>
									<div class="col-md-2 col-sm-2 col-2 mx-auto "></div>
								</div>
							</div>
						</div>
					</div>






					<!-- EVENTO Torneo -->
					<div class="container-fluid">
						<div class="row">
							<div class="col-md-12 col-sm-12 col-12 mx-auto"
								id="torneoContainer" style="display: none;">
								<!-- Botones de control -->
								<div
									class="d-flex justify-content-between align-items-center mb-3">
									<div class="col-md-11 col-sm-12 col-12 mx-auto   m-1"
										style="display: flex; align-items: center; gap: 10px;">
										<button id="volverAContenidoC" class=" mr-auto botonVolver"
											style="margin-right: 2.5vw">Volver</button>
										<button id="mostrarFiltrosTorneo"
											class=" mr-auto botonFiltrar" style="background-color: black">Mostrar
											Filtros</button>

										<button id="crearEventoTorneo" class=" mr-auto botonFiltrar"
											style="background-color: green">Crear Torneo</button>

									</div>
								</div>

								<!-- Formulario crear torneo -->
								<div class="container-fluid">
									<div class="row  mb-1" id="crearTorneoContainer"
										style="display: none;">
										<div class=" col-md-12 col-sm-12 col-12  mx-auto ">

											<div class="registrarFormulario ">
												<form action="torneo" method="POST"
													enctype="multipart/form-data">
													<input type="hidden" name="accion" value="crear">
													<input type="hidden" name="instalacionId"
														value="<%=instalacionId%>">
													<table class="tablaFormulario "
														style="width: 50vw; background-color: #dedede; color: black; box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.6), 0px -4px 8px rgba(0, 0, 0, 0.15)">

														<tbody>
															<tr>

																<td><label for="nombreTorneo"
																	class="formularioLabel ">Nombre Torneo: </label> <input
																	type="text"
																	style="font-size: 1vw; border: 1px solid #818181; height: 2.5vw;"
																	class="form-control" id="nombreTorneo"
																	name="nombreTorneo" required></td>

																<td><label for="modalidad" class="formularioLabel">
																		Modalidad: </label> <select class="form-select" id="modalidad"
																	name="modalidad" required
																	style="font-size: 1vw; border: 1px solid #818181;">
																		<option style="font-size: 1.2vw" value="Futbol5">Fútbol
																			5</option>
																		<option style="font-size: 1.2vw" value="Futbol7">Fútbol
																			7</option>
																		<option style="font-size: 1.2vw" value="Futbol11">Fútbol
																			11</option>
																</select></td>


															</tr>
															<tr>
																<td><label for="fechaInicioTorneo"
																	class="formularioLabel ">Fecha inicio: </label> <input
																	type="date"
																	style="font-size: 1vw; border: 1px solid #818181; height: 2.5vw;"
																	class="form-control" id="fechaInicioTorneo"
																	name="fechaInicioTorneo" required></td>
																<td><label for="descripcionUsuario"
																	class="formularioLabel ">Descripción torneo</label> <textarea
																		class="form-control"
																		style="font-size: 1vw; border: 1px solid #818181;"
																		id="descripcionUsuario" name="descripcionTorneo"
																		rows="3"></textarea></td>
															</tr>

														</tbody>
													</table>

													<div class="text-center mt-4  mb-4">
														<button type="submit" class=" botonRegistrarCabecera">
															<i>Nuevo torneo</i>
														</button>

													</div>
												</form>
											</div>
										</div>
									</div>
								</div>


								<!-- Filtros de búsqueda -->
								<div id="filtrosTorneo" class="filaFiltrar mb-4"
									style="display: none;">

									<div class="filtroItem">
										<label for="buscarNombre" class="labelFiltrar"><b>-Buscar
												por Nombre:</b></label> <input type="text" id="buscarNombre"
											class="inputFiltrar" placeholder="Buscar por Nombre">
									</div>

									<div class="filtroItem">
										<label for="buscarModalidad" class="labelFiltrar"><b>-Buscar
												por Modalidad:</b></label> <input type="text" id="buscarModalidad"
											class="inputFiltrar" placeholder="Buscar por Modalidad">
									</div>
									<div class="filtroItem">
										<label for="buscarFechaInicio" class="labelFiltrar"><b>-Buscar
												por fecha inicio:</b></label> <input type="text" id="buscarFechaInicio"
											class="inputFiltrar" placeholder="Buscar por Fecha Inicio">
									</div>
								</div>

								<!-- Tabla de torneo -->

								<table
									class="tablaAdmin tablaAdmin--instalaciones w-100 mb-3 mx-auto">
									<thead class="tablaAdmin__head">
										<tr>
											<th>TORNEO</th>
											<th>MODALIDAD</th>
											<th>FECHA INICIO</th>
											<th>Nº CLUBES</th>
											<th>ACTIVAR</th>
											<th class="tablaAdmin__opciones">OPCIONES</th>
										</tr>
									</thead>
									<tbody id="tablaCuerpoTorneo"></tbody>
								</table>


								<!-- Paginación -->
								<div id="tablaCuerpoTorneo-paginacion"
									class="contenedorPaginacion mb-4 d-flex justify-content-center"></div>

							</div>
						</div>
					</div>


				</div>
			</div>
		</div>

		<!-- Modal Editar Torneo -->
		<div class="modal fade" id="modalEditarTorneo" tabindex="-1"
			aria-labelledby="modalEditarTorneoLabel" aria-hidden="true">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">

					<!-- HEADER -->
					<div class="modal-header">
						<h5 class="modal-title w-100 text-center"
							id="modalEditarTorneoLabel">
							<b>EDITAR TORNEO</b>
						</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Cerrar"></button>
					</div>

					<!-- BODY -->
					<div class="modal-body">
						<form id="formEditarTorneo" method="POST">

							<input type="hidden" id="editIdTorneo" name="idTorneo">

							<div class="container-fluid">

								<!-- Primera fila: Nombre y Modalidad -->
								<div class="row mb-3">
									<div class="col-md-6 mb-3">
										<label for="editNombreTorneo"><b>Nombre del Torneo</b></label>
										<input type="text" class="form-control" id="editNombreTorneo"
											name="nombreTorneo" style="border: 2px solid #aaa;" required>
									</div>

									<div class="col-md-6 mb-3">
										<label for="editModalidad"><b>Modalidad</b></label> <select
											class="form-select" id="editModalidad" name="modalidad"
											style="border: 2px solid #aaa;" required>
											<option value="Futbol5">Fútbol 5</option>
											<option value="Futbol7">Fútbol 7</option>
											<option value="Futbol11">Fútbol 11</option>

										</select>
									</div>
								</div>

								<!-- Segunda fila: Fecha inicio y Fecha fin -->
								<div class="row mb-3">
									<div class="col-md-6">
										<label for="editFechaInicio"><b>Fecha de Inicio</b></label> <input
											type="date" class="form-control" id="editFechaInicio"
											name="fechaInicioTorneo" required>
									</div>
									<div class="col-md-6">
										<label for="editFechaFin"><b>Fecha de Fin</b></label> <input
											type="date" class="form-control" id="editFechaFin"
											name="fechaFinTorneo">
									</div>
								</div>

								<div class="row mb-3">
									<div class="col-md-6">
										<label for="editEstaActivo"><b>Está Activo</b></label> <select
											id="editEstaActivo" name="estaActivo" class="form-select">
											<option value="true">Sí</option>
											<option value="false">No</option>
										</select>
									</div>
								</div>

								<!-- Tercera fila: Descripción -->
								<div class="row mb-3">
									<div class="col-md-12 mb-3">
										<label for="editDescripcionTorneo"><b>Descripción</b></label>
										<textarea class="form-control" id="editDescripcionTorneo"
											name="descripcionTorneo" rows="3"
											style="border: 2px solid #aaa;"></textarea>
									</div>
								</div>

							</div>

						</form>
					</div>

					<!-- FOOTER -->
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">Cancelar</button>
						<button type="submit" class="btn btn-primary"
							form="formEditarTorneo">Guardar Cambios</button>
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
						<div class="col-md-3 col-sm-3  g-2 pieDePagina p-3 pb-1  "
							style="margin-left: 2vw">
							<p style="text-decoration: underline; font-size: 1.5vw">SOBRE
								NOSOTROS</p>
							<p style="font-size: 1.05vw">Somos una plataforma innovadora
								para conectar jugadores, organizar torneos y reservar campos de
								fútbol. Únete a nuestra comunidad deportiva y vive la pasión por
								el fútbol.</p>
						</div>
						<div class="col-md-3 col-sm-3 g-2 pieDePagina p-3 pb-1 ">
							<p
								style="text-decoration: underline; font-family: 'Open Sans', sans-serif; font-size: 1.5vw">ENLACES
								ÚTILES</p>
							<pre
								style="text-decoration: underline; font-family: 'Open Sans', sans-serif; font-size: 1.05vw">
<a href="${pageContext.request.contextPath}/instalacion" style="color: white">Inicio</a>
<a href="${pageContext.request.contextPath}/instalacion/eventos" style="color: white">Eventos</a></pre>
						</div>
						<div class="col-md-3 col-sm-3 g-2 pieDePagina p-3 pb-1">
							<p style="text-decoration: underline; font-size: 1.5vw">CONTACTO</p>
							<pre
								style="font-family: 'Open Sans', sans-serif; font-size: 1.05vw">futboldebarriosevilla@gmail.com
</pre>
						</div>	
						<div class="col-md-2 col-sm-2 g-2 pieDePagina p-3 pb-1">
							<p
								style="text-decoration: underline; font-family: 'Open Sans', sans-serif; font-size: 1.5vw">
								SÍGUENOS</p>
							<a href="https://www.instagram.com/futboldebarriosevilla/"
								style="text-decoration: none"><svg
									xmlns="http://www.w3.org/2000/svg" width="1.8vw" height="1.6vw"
									fill="currentColor" style="color: white; margin-right: 0.5vw"
									class="bi bi-instagram" viewBox="0 0 16 16">
									<path
										d="M8 0C5.829 0 5.556.01 4.703.048 3.85.088 3.269.222 2.76.42a3.9 3.9 0 0 0-1.417.923A3.9 3.9 0 0 0 .42 2.76C.222 3.268.087 3.85.048 4.7.01 5.555 0 5.827 0 8.001c0 2.172.01 2.444.048 3.297.04.852.174 1.433.372 1.942.205.526.478.972.923 1.417.444.445.89.719 1.416.923.51.198 1.09.333 1.942.372C5.555 15.99 5.827 16 8 16s2.444-.01 3.298-.048c.851-.04 1.434-.174 1.943-.372a3.9 3.9 0 0 0 1.416-.923c.445-.445.718-.891.923-1.417.197-.509.332-1.09.372-1.942C15.99 10.445 16 10.173 16 8s-.01-2.445-.048-3.299c-.04-.851-.175-1.433-.372-1.941a3.9 3.9 0 0 0-.923-1.417A3.9 3.9 0 0 0 13.24.42c-.51-.198-1.092-.333-1.943-.372C10.443.01 10.172 0 7.998 0zm-.717 1.442h.718c2.136 0 2.389.007 3.232.046.78.035 1.204.166 1.486.275.373.145.64.319.92.599s.453.546.598.92c.11.281.24.705.275 1.485.039.843.047 1.096.047 3.231s-.008 2.389-.047 3.232c-.035.78-.166 1.203-.275 1.485a2.5 2.5 0 0 1-.599.919c-.28.28-.546.453-.92.598-.28.11-.704.24-1.485.276-.843.038-1.096.047-3.232.047s-2.39-.009-3.233-.047c-.78-.036-1.203-.166-1.485-.276a2.5 2.5 0 0 1-.92-.598 2.5 2.5 0 0 1-.6-.92c-.109-.281-.24-.705-.275-1.485-.038-.843-.046-1.096-.046-3.233s.008-2.388.046-3.231c.036-.78.166-1.204.276-1.486.145-.373.319-.64.599-.92s.546-.453.92-.598c.282-.11.705-.24 1.485-.276.738-.034 1.024-.044 2.515-.045zm4.988 1.328a.96.96 0 1 0 0 1.92.96.96 0 0 0 0-1.92m-4.27 1.122a4.109 4.109 0 1 0 0 8.217 4.109 4.109 0 0 0 0-8.217m0 1.441a2.667 2.667 0 1 1 0 5.334 2.667 2.667 0 0 1 0-5.334" />
</svg> </a> <a href="https://x.com/FDB_Sevilla" style="text-decoration: none"><svg
									xmlns="http://www.w3.org/2000/svg" width="1.8vw" height="1.6vw"
									fill="currentColor"
									style="color: white; margin-right: 0.5vw; text-decoration: none"
									class="bi bi-twitter-x" viewBox="0 0 16 16">
  <path
										d="M12.6.75h2.454l-5.36 6.142L16 15.25h-4.937l-3.867-5.07-4.425 5.07H.316l5.733-6.57L0 .75h5.063l3.495 4.633L12.601.75Zm-.86 13.028h1.36L4.323 2.145H2.865z" />
</svg> </a> <a href="https://www.youtube.com/channel/UCfzHaUblCQl7lzp4CHXR9ug"
								style="text-decoration: none"><svg
									xmlns="http://www.w3.org/2000/svg" width="1.8vw" height="1.8vw"
									fill="currentColor" style="color: white; margin-right: 0.5vw;"
									class="bi bi-youtube" viewBox="0 0 16 16">
  <path
										d="M8.051 1.999h.089c.822.003 4.987.033 6.11.335a2.01 2.01 0 0 1 1.415 1.42c.101.38.172.883.22 1.402l.01.104.022.26.008.104c.065.914.073 1.77.074 1.957v.075c-.001.194-.01 1.108-.082 2.06l-.008.105-.009.104c-.05.572-.124 1.14-.235 1.558a2.01 2.01 0 0 1-1.415 1.42c-1.16.312-5.569.334-6.18.335h-.142c-.309 0-1.587-.006-2.927-.052l-.17-.006-.087-.004-.171-.007-.171-.007c-1.11-.049-2.167-.128-2.654-.26a2.01 2.01 0 0 1-1.415-1.419c-.111-.417-.185-.986-.235-1.558L.09 9.82l-.008-.104A31 31 0 0 1 0 7.68v-.123c.002-.215.01-.958.064-1.778l.007-.103.003-.052.008-.104.022-.26.01-.104c.048-.519.119-1.023.22-1.402a2.01 2.01 0 0 1 1.415-1.42c.487-.13 1.544-.21 2.654-.26l.17-.007.172-.006.086-.003.171-.007A100 100 0 0 1 7.858 2zM6.4 5.209v4.818l4.157-2.408z" />

							</a> <a href="https://www.tiktok.com/@fdb_sevilla"
								style="text-decoration: none"> <svg
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

				<div class=" col-12 d-block d-md-none d-sm-none">
					<div class="row">

						<div
							class=" col-4 d-block d-md-none d-sm-none g-2 pieDePagina p-2  ">
							<p
								style="text-decoration: underline; font-family: 'Open Sans', sans-serif; font-size: 2vw">ENLACES
								ÚTILES</p>
							<pre
								style="text-decoration: underline; font-family: 'Open Sans', sans-serif; font-size: 1.6vw">
<a href="${pageContext.request.contextPath}/instalacion" style="color: white">Inicio</a>
<a href="${pageContext.request.contextPath}/instalacion/eventos" style="color: white">Eventos</a></pre>
						</div>
						<div
							class="col-4 d-block d-md-none d-sm-none g-2 pieDePagina p-2 ">
							<p style="text-decoration: underline; font-size: 2vw">CONTACTO</p>
							<pre
								style="font-family: 'Open Sans', sans-serif; font-size: 1.6vw">futboldebarriosevilla@gmail.com
</pre>
						</div>
						<div
							class="col-4 d-block d-md-none d-sm-none g-2 pieDePagina p-2 ">
							<p
								style="text-decoration: underline; font-family: 'Open Sans', sans-serif; font-size: 2vw">
								SÍGUENOS</p>


							<a href="https://www.instagram.com/futboldebarriosevilla/"
								style="text-decoration: none"><svg
									xmlns="http://www.w3.org/2000/svg" width="2.2vw" height="2vw"
									fill="currentColor" style="color: white; margin-right: 0.5vw"
									class="bi bi-instagram" viewBox="0 0 16 16">
									<path
										d="M8 0C5.829 0 5.556.01 4.703.048 3.85.088 3.269.222 2.76.42a3.9 3.9 0 0 0-1.417.923A3.9 3.9 0 0 0 .42 2.76C.222 3.268.087 3.85.048 4.7.01 5.555 0 5.827 0 8.001c0 2.172.01 2.444.048 3.297.04.852.174 1.433.372 1.942.205.526.478.972.923 1.417.444.445.89.719 1.416.923.51.198 1.09.333 1.942.372C5.555 15.99 5.827 16 8 16s2.444-.01 3.298-.048c.851-.04 1.434-.174 1.943-.372a3.9 3.9 0 0 0 1.416-.923c.445-.445.718-.891.923-1.417.197-.509.332-1.09.372-1.942C15.99 10.445 16 10.173 16 8s-.01-2.445-.048-3.299c-.04-.851-.175-1.433-.372-1.941a3.9 3.9 0 0 0-.923-1.417A3.9 3.9 0 0 0 13.24.42c-.51-.198-1.092-.333-1.943-.372C10.443.01 10.172 0 7.998 0zm-.717 1.442h.718c2.136 0 2.389.007 3.232.046.78.035 1.204.166 1.486.275.373.145.64.319.92.599s.453.546.598.92c.11.281.24.705.275 1.485.039.843.047 1.096.047 3.231s-.008 2.389-.047 3.232c-.035.78-.166 1.203-.275 1.485a2.5 2.5 0 0 1-.599.919c-.28.28-.546.453-.92.598-.28.11-.704.24-1.485.276-.843.038-1.096.047-3.232.047s-2.39-.009-3.233-.047c-.78-.036-1.203-.166-1.485-.276a2.5 2.5 0 0 1-.92-.598 2.5 2.5 0 0 1-.6-.92c-.109-.281-.24-.705-.275-1.485-.038-.843-.046-1.096-.046-3.233s.008-2.388.046-3.231c.036-.78.166-1.204.276-1.486.145-.373.319-.64.599-.92s.546-.453.92-.598c.282-.11.705-.24 1.485-.276.738-.034 1.024-.044 2.515-.045zm4.988 1.328a.96.96 0 1 0 0 1.92.96.96 0 0 0 0-1.92m-4.27 1.122a4.109 4.109 0 1 0 0 8.217 4.109 4.109 0 0 0 0-8.217m0 1.441a2.667 2.667 0 1 1 0 5.334 2.667 2.667 0 0 1 0-5.334" />
</svg> </a> <a href="https://x.com/FDB_Sevilla" style="text-decoration: none"><svg
									xmlns="http://www.w3.org/2000/svg" width="2.2vw" height="2vw"
									fill="currentColor"
									style="color: white; margin-right: 0.5vw; text-decoration: none"
									class="bi bi-twitter-x" viewBox="0 0 16 16">
  <path
										d="M12.6.75h2.454l-5.36 6.142L16 15.25h-4.937l-3.867-5.07-4.425 5.07H.316l5.733-6.57L0 .75h5.063l3.495 4.633L12.601.75Zm-.86 13.028h1.36L4.323 2.145H2.865z" />
</svg> </a> <a href="https://www.youtube.com/channel/UCfzHaUblCQl7lzp4CHXR9ug"
								style="text-decoration: none"><svg
									xmlns="http://www.w3.org/2000/svg" width="2.2vw" height="2vw"
									fill="currentColor" style="color: white; margin-right: 0.5vw;"
									class="bi bi-youtube" viewBox="0 0 16 16">
  <path
										d="M8.051 1.999h.089c.822.003 4.987.033 6.11.335a2.01 2.01 0 0 1 1.415 1.42c.101.38.172.883.22 1.402l.01.104.022.26.008.104c.065.914.073 1.77.074 1.957v.075c-.001.194-.01 1.108-.082 2.06l-.008.105-.009.104c-.05.572-.124 1.14-.235 1.558a2.01 2.01 0 0 1-1.415 1.42c-1.16.312-5.569.334-6.18.335h-.142c-.309 0-1.587-.006-2.927-.052l-.17-.006-.087-.004-.171-.007-.171-.007c-1.11-.049-2.167-.128-2.654-.26a2.01 2.01 0 0 1-1.415-1.419c-.111-.417-.185-.986-.235-1.558L.09 9.82l-.008-.104A31 31 0 0 1 0 7.68v-.123c.002-.215.01-.958.064-1.778l.007-.103.003-.052.008-.104.022-.26.01-.104c.048-.519.119-1.023.22-1.402a2.01 2.01 0 0 1 1.415-1.42c.487-.13 1.544-.21 2.654-.26l.17-.007.172-.006.086-.003.171-.007A100 100 0 0 1 7.858 2zM6.4 5.209v4.818l4.157-2.408z" />

							</a> <a href="https://www.tiktok.com/@fdb_sevilla"
								style="text-decoration: none"> <svg
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
			</div>
		</div>
	</footer>




	<script>
const instalacionId = <%=instalacionId%>;

// Mensajes que desaparecen solos
window.onload = function () {
    setTimeout(function () {
        const mensajes = document.querySelectorAll('.fade-message');
        mensajes.forEach(function (el) {
            el.style.transition = "opacity 1s ease-out";
            el.style.opacity = '0';
            setTimeout(() => el.remove(), 1000);
        });
    }, 2000);
};

// Al pulsar TORNEO: oculta recuadros y muestra contenedor
document.getElementById("botonTorneo").addEventListener("click", function() {
    document.getElementById("botonLiga").parentElement.style.display = "none";
    document.getElementById("botonTorneo").parentElement.style.display = "none";
    document.getElementById("torneoContainer").style.display = "block";
});


// Filtrar tabla
function filtrarTabla(idFiltro, columnaIndex) {
    const valorFiltro = document.getElementById(idFiltro).value.toLowerCase();
    const filas = document.querySelectorAll("#tablaCuerpoTorneo tr");

    filas.forEach(fila => {
        const celdas = fila.getElementsByTagName("td");
        if (!celdas[columnaIndex]) return;

        fila.style.display = celdas[columnaIndex].textContent
            .toLowerCase()
            .includes(valorFiltro) ? "" : "none";
    });

    paginarTabla('tablaCuerpoTorneo', 8);
}

document.getElementById("buscarNombre").addEventListener("input", () => filtrarTabla("buscarNombre", 0));
document.getElementById("buscarModalidad").addEventListener("input", () => filtrarTabla("buscarModalidad", 1));
document.getElementById("buscarFechaInicio").addEventListener("input", () => filtrarTabla("buscarFechaInicio", 2));

// Mostrar/ocultar formulario de crear torneo
document.addEventListener("DOMContentLoaded", function () {
    const btnCrear = document.getElementById("crearEventoTorneo");
    const contenedorFormulario = document.getElementById("crearTorneoContainer");
    btnCrear.addEventListener("click", function () {
        contenedorFormulario.style.display = (contenedorFormulario.style.display === "none" || contenedorFormulario.style.display === "") ? "block" : "none";
    });
    
 // Mostrar u ocultar filtros
    document.getElementById("mostrarFiltrosTorneo").addEventListener("click", function() {
        const filtros = document.getElementById("filtrosTorneo");
        if (filtros.style.display === "none") {
            filtros.style.display = "flex";
            this.textContent = "Ocultar Filtros";
        } else {
            filtros.style.display = "none";
            this.textContent = "Mostrar Filtros";
        }
    });

});

// Volver a vista principal (oculta contenedor y muestra recuadros)
document.getElementById("volverAContenidoC").addEventListener("click", function() {
    document.getElementById("torneoContainer").style.display = "none";
    document.getElementById("botonLiga").parentElement.style.display = "flex";
    document.getElementById("botonTorneo").parentElement.style.display = "flex";
});

// Cargar torneos
$(document).ready(function() {
    cargarTorneos();
});

function cargarTorneos() {
    $.ajax({
        url: '<%=request.getContextPath()%>/instalacion/eventos',
        method: 'GET',
        dataType: 'json',
        data: { instalacionId: instalacionId },
        xhrFields: { withCredentials: true },
        success: function (data) {
            const tbody = document.getElementById("tablaCuerpoTorneo");
            tbody.innerHTML = "";

            if (!data || !Array.isArray(data) || data.length === 0) {
                tbody.innerHTML = `
                    <tr>
                        <td colspan="6" style="
                            text-align: center; 
                            color: #ff6600; 
                            font-weight: bold; 
                            font-style: italic; 
                            padding: 1em;
                        ">
                            ⚠️ No hay torneos disponibles
                        </td>
                    </tr>
                `;
                return;
            }


            function inscritosCount(str) {
                if (!str) return 0;
                const m = str.match(/(\d+)/);
                return m ? parseInt(m[0], 10) : 0;
            }

            data.forEach(function (torneo) {
                const fechaInicio = torneo.fechaInicioTorneo ? new Date(torneo.fechaInicioTorneo) : null;
                const fechaInicioFormateada = fechaInicio ? fechaInicio.toLocaleDateString() : '';

                const inscritosTexto = torneo.clubesInscritos || torneo.progresoEquipos || '0 / 16';
                const inscritosNum = inscritosCount(inscritosTexto);

                const estaActivo = (torneo.estaActivo === true || torneo.estaActivo === 'true');

                // Nombre clicable si el torneo está activo
                const nombreHtml = estaActivo
                    ? '<span class="nombre-torneo-link" data-torneo-id="' + torneo.idTorneo + '" ' +
                      'style="cursor:pointer; color:green; text-decoration:underline; font-weight:bold;">' + (torneo.nombreTorneo || '') + '</span>'
                    : (torneo.nombreTorneo || '');

                // Botón Activar
                let botonActivarHtml = '';
                if (estaActivo) {
                    botonActivarHtml = '<button class="btn-activar" data-torneo-id="' + torneo.idTorneo +
                        '" disabled style="background:#2ecc71;color:white;border:none;padding:5px 8px;border-radius:5px;">Activo</button>';
                } else if (inscritosNum === 16) {
                    botonActivarHtml = '<button class="btn-activar" data-torneo-id="' + torneo.idTorneo +
                        '" style="background:#28a745;color:white;border:none;padding:5px 8px;border-radius:5px;cursor:pointer;">Activar</button>';
                } else {
                    botonActivarHtml = '<button class="btn-activar" data-torneo-id="' + torneo.idTorneo +
                        '" disabled style="background:lightgray;color:#666;border:none;padding:5px 8px;border-radius:5px;">Activar</button>';
                }

                // Opciones (Eliminar + Editar)
                const opcionesHtml =
                    '<div class="tablaAdmin__acciones">' +
                        '<button class="tablaAdmin__btn tablaAdmin__btn--eliminar btnEliminar" data-id="' + torneo.idTorneo + '">' +
                            '<i class="bi bi-trash3-fill"></i>' +
                        '</button>' +
                        '<button class="tablaAdmin__btn tablaAdmin__btn--editar btnEditar" data-usuario=\'' + JSON.stringify(torneo) + '\'>' +
                            '<i class="bi bi-pencil-square"></i>' +
                        '</button>' +
                    '</div>';

                // Crear fila
                const fila = document.createElement("tr");
                fila.id = "fila-" + torneo.idTorneo;
                fila.innerHTML =
                    "<td>" + nombreHtml + "</td>" +
                    "<td>" + (torneo.modalidad || '') + "</td>" +
                    "<td>" + fechaInicioFormateada + "</td>" +
                    "<td style='color:green;'>" + inscritosTexto + "</td>" +
                    "<td>" + botonActivarHtml + "</td>" +
                    "<td class='tablaAdmin__opciones'>" + opcionesHtml + "</td>";

                tbody.appendChild(fila);
            });

            // Paginación
            paginarTabla('tablaCuerpoTorneo', 8);

            // Eventos
            $(document).off('click', '.btn-activar').on('click', '.btn-activar', function () {
                const $btn = $(this);
                const torneoId = $btn.data('torneo-id');
                if ($btn.prop('disabled')) return;

                $.ajax({
                    url: '<%=request.getContextPath()%>/instalacion/eventos',
                    method: 'POST',
                    data: { accion: 'activar', idTorneo: torneoId },
                    success: function () {
                        alert("Torneo activado");
                        cargarTorneos();
                    },
                    error: function (xhr) {
                        alert("Error al activar torneo: " + xhr.status);
                        console.error(xhr.responseText);
                    }
                });
            });

            $(document).off('click', '.nombre-torneo-link').on('click', '.nombre-torneo-link', function () {
                const torneoId = $(this).data('torneo-id');

                // Crear un formulario POST temporal para enviar el torneoId
                const form = document.createElement("form");
                form.method = "POST";
                form.action = '<%= request.getContextPath() %>' + "/instalacion/torneo";

                const input = document.createElement("input");
                input.type = "hidden";
                input.name = "torneoId"; // debe coincidir con doPost
                input.value = torneoId;

                form.appendChild(input);
                document.body.appendChild(form);
                form.submit();
            });

       

            $(document).off('click', '.btnEditar').on('click', '.btnEditar', function () {
                const torneo = $(this).data('usuario');
                abrirModalEditarTorneo(torneo); // Función que deberías tener para editar
            });

        },
        error: function (xhr) {
            console.error("Error cargando torneos:", xhr.status, xhr.responseText);
            document.getElementById("tablaCuerpoTorneo").innerHTML =
                '<tr><td colspan="6">Error cargando torneos</td></tr>';
        }
    });
}


function paginarTabla(tablaBodyId, filasPorPagina = 8) {
    const tbody = document.getElementById(tablaBodyId);
    if (!tbody) return;

    const todasFilas = Array.from(tbody.querySelectorAll('tr'));
    const filasVisibles = todasFilas.filter(f => f.style.display !== 'none');
    const totalPaginas = Math.ceil(filasVisibles.length / filasPorPagina);
    let paginaActual = 1;

    const pagDiv = document.getElementById(tablaBodyId + '-paginacion');
    if (!pagDiv) return;

    function mostrarPagina(pagina) {
        const inicio = (pagina - 1) * filasPorPagina;
        const fin = inicio + filasPorPagina;

        filasVisibles.forEach((fila, i) => fila.style.display = (i >= inicio && i < fin) ? '' : 'none');

        pagDiv.innerHTML = '';

        const btnAnterior = document.createElement('button');
        btnAnterior.textContent = '< Anterior';
        btnAnterior.classList.add('paginacion'); 
        btnAnterior.disabled = pagina === 1;
        btnAnterior.addEventListener('click', () => {
            paginaActual--;
            mostrarPagina(paginaActual);
        });

        const btnSiguiente = document.createElement('button');
        btnSiguiente.textContent = 'Siguiente >';
        btnSiguiente.classList.add('paginacion'); 
        btnSiguiente.disabled = pagina === totalPaginas;
        btnSiguiente.addEventListener('click', () => {
            paginaActual++;
            mostrarPagina(paginaActual);
        });

        const spanInfo = document.createElement('span');
        spanInfo.textContent = ' Página ' + pagina + ' de ' + totalPaginas + ' ';
        spanInfo.style.margin = '0 10px';

        pagDiv.appendChild(btnAnterior);
        pagDiv.appendChild(spanInfo);
        pagDiv.appendChild(btnSiguiente);
        pagDiv.style.display = 'flex';
        pagDiv.style.alignItems = 'center';
        pagDiv.style.justifyContent = 'center';
        pagDiv.style.gap = '0.5vw'; 
    }

    mostrarPagina(paginaActual);
}



// Crear torneo vía AJAX (sin recargar página)
$('#crearTorneoContainer form').submit(function(e){
    e.preventDefault();
    let formData = $(this).serialize();
    formData += '&instalacionId=' + instalacionId; 
    $.ajax({
        url: '<%=request.getContextPath()%>/instalacion/eventos',
        method: 'POST',
        data: formData + '&accion=crear',
        success: function() {
            $('#crearTorneoContainer form')[0].reset();
            $('#crearTorneoContainer').hide();
            cargarTorneos();
        },
        error: function(xhr, status, error){
            console.error('Error al crear torneo:', error);
        }
    });
});

// Eliminar torneo
$('#tablaCuerpoTorneo').on('click', '.btnEliminar', function () {
    const idTorneo = $(this).data('id');
    if (confirm("¿Seguro que deseas eliminar este torneo?")) {
    	$.ajax({
    	    url: '<%=request.getContextPath()%>/instalacion/eventos?idTorneo=' + idTorneo,
    	    method: 'DELETE',
            success: function () {
                $('#fila-' + idTorneo).remove();
            },
            error: function (xhr, status, error) {
                console.error('Error al eliminar torneo:', error);
            }
        });
    }
});



function abrirGmail() {
	const email = "futboldebarriosevilla@gmail.com";
	const subject = "Titulo del Asunto: ";
	const body = "Escriba aqui el mensaje....";

	const url = "https://mail.google.com/mail/?view=cm&fs=1&to="
			+ encodeURIComponent(email) + "&su="
			+ encodeURIComponent(subject) + "&body="
			+ encodeURIComponent(body);

	window.open(url, "_blank");
}
function cerrarModalEditar() {
    $('#modalEditarTorneo').fadeOut();
}


function abrirModalEditarTorneo(torneo) {
    $('#editIdTorneo').val(torneo.idTorneo);
    $('#editNombreTorneo').val(torneo.nombreTorneo);
    $('#editDescripcionTorneo').val(torneo.descripcionTorneo);
    $('#editModalidad').val(torneo.modalidad);
    $('#editFechaInicio').val(torneo.fechaInicioTorneo || "");
    $('#editFechaFin').val(torneo.fechaFinTorneo || "");
    $('#editEstaActivo').val(torneo.estaActivo.toString());

    const modal = new bootstrap.Modal(
        document.getElementById('modalEditarTorneo')
    );
    modal.show();
}


$('#formEditarTorneo').submit(function (e) {
    e.preventDefault();

    let datos = $(this).serialize();
    datos += '&accion=modificar';

    $.ajax({
        url: '<%=request.getContextPath()%>/instalacion/eventos',
        method: 'POST',
        data: datos,
        success: function () {
            const modalEl = document.getElementById('modalEditarTorneo');
            const modal = bootstrap.Modal.getInstance(modalEl);
            modal.hide();

            cargarTorneos();
            alert("Torneo modificado correctamente");
        },
        error: function (xhr) {
            alert("Error al modificar torneo");
            console.error(xhr.responseText);
        }
    });
});




	
</script>



<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>