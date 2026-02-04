
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
Long torneoId = (Long) session.getAttribute("torneoIdSeleccionado");
String nombreInstalacion = (String) session.getAttribute("nombreInstalacion");

if (torneoId == null) {
	response.sendRedirect(request.getContextPath() + "/login?error=torneoNoSeleccionado");
	return;
}
%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/Css/Estilo.css">

<!-- Bootstrap CSS (solo una vez) -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet" crossorigin="anonymous">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css"
	rel="stylesheet">



<!-- jQuery CDN -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>


<title>FUTBOL DE BARRIO</title>
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
											futboldebarriosevilla@gmail.com
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
										<div class="col-sm-2 col-md-2 cabeceraArriba"
											style="justify-content: left;">
											<a href="">
												<button type="button" class="botonCabeceraContactar"
													onclick="abrirGmail()">CONTACTAR</button>
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
											<a href="Instalacion.jsp" class="letraCabeceraAbajo"">INICIO</a>
										</div>
										<div class="col-sm-4 col-md-4 cabeceraAbajo ">
											<a href="#" class="letraCabeceraAbajo seccion-bloqueada">
												RESERVAS <span class="tooltip-text">Sección en
													desarrollo</span>
											</a>

										</div>
										<div class="col-sm-3 col-md-3 cabeceraAbajo ">
											<a href="EventoInstalacion.jsp" class="letraCabeceraAbajo"
												style="color: #d4af37;">EVENTOS</a>
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
														href="#">Idioma<span class="tooltip-text">Sección
																en desarrollo</span>
													</a></li>
													<li><a class="dropdown-item seccion-bloqueada"
														href="#">Ayuda <span class="tooltip-text">Sección
																en desarrollo</span></a></li>
													<li><a class="dropdown-item seccion-bloqueada"
														href="#">Configuración <span class="tooltip-text">Sección
																en desarrollo</span>
													</a></li>
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
							<img
								src="${pageContext.request.contextPath}/Imagenes/LOGOWEB.PNG"></img>
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
									<a href="Instalacion.jsp" class="letraCabeceraAbajo "
										style="text-decoration: none; font-size: 2.5vw;">INICIO</a>
								</div>

								<!-- columna medio: -->

								<div
									class="col-3 d-flex justify-content-start align-items-center ps-4 ">
									<a href="EventoInstalacion.jsp" class="letraCabeceraAbajo "
										style="text-decoration: none; font-size: 2.5vw; color: #d4af37;">EVENTOS</a>
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
											<li><a class="dropdown-item" href="logout"
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
		<div class="contenedorTorneo">
			<!-- Botón Volver a EventoInstalacion -->
			<div>
				<button type="button" class="btn botonVolver"
					onclick="window.location.href='EventoInstalacion.jsp?id=<%=session.getAttribute("instalacionId")%>';">
					Volver</button>

			</div>

			<h2 class="tituloTorneo">
				🏆<b>-TORNEO: </b> <span
					style="font-weight: 700; text-decoration: underline"
					id="nombreTorneo"> Cargando torneo... </span>
			</h2>
			<div class="contenedorFlex">
				<div class="separadorVisual"></div>
				<!-- Camino a la Copa (Bracket) -->
				<div class="filaTorneo">
					<!-- Octavos izquierda -->
					<div class="columnaTorneo">
						<div class="casillaPartido" data-id="octavos1"></div>
						<div class="celdaVacia"></div>
						<div class="casillaPartido" data-id="octavos2"></div>
						<div class="celdaVacia"></div>
						<div class="casillaPartido" data-id="octavos3"></div>
						<div class="celdaVacia"></div>
						<div class="casillaPartido" data-id="octavos4"></div>
					</div>
					<!-- Cuartos izquierda -->
					<div class="columnaTorneo">
						<div class="celdaVacia"></div>
						<div class="casillaPartido" data-id="cuartos1"></div>
						<div class="celdaVacia"></div>
						<div class="celdaVacia"></div>
						<div class="celdaVacia"></div>
						<div class="casillaPartido" data-id="cuartos2"></div>
						<div class="celdaVacia"></div>
					</div>
					<!-- Semifinal izquierda -->
					<div class="columnaTorneo">
						<div class="celdaVacia"></div>
						<div class="celdaVacia"></div>
						<div class="celdaVacia"></div>
						<div class="casillaPartido" data-id="semifinal1"></div>
						<div class="celdaVacia"></div>
						<div class="celdaVacia"></div>
						<div class="celdaVacia"></div>
					</div>
					<!-- Final -->
					<div class="columnaTorneo">
						<div class="celdaVacia"></div>
						<div class="celdaVacia"></div>
						<div class="celdaVacia"></div>
						<div class="casillaPartido" data-id="partidoFinal"></div>
						<div class="celdaVacia"></div>
						<div class="casillaPartido" data-id="tercerpuesto"></div>
						<div class="celdaVacia"></div>
					</div>
					<!-- Semifinal derecha -->
					<div class="columnaTorneo">
						<div class="celdaVacia"></div>
						<div class="celdaVacia"></div>
						<div class="celdaVacia"></div>
						<div class="casillaPartido" data-id="semifinal2"></div>
						<div class="celdaVacia"></div>
						<div class="celdaVacia"></div>
						<div class="celdaVacia"></div>
					</div>
					<!-- Cuartos derecha -->
					<div class="columnaTorneo">
						<div class="celdaVacia"></div>
						<div class="casillaPartido" data-id="cuartos3"></div>
						<div class="celdaVacia"></div>
						<div class="celdaVacia"></div>
						<div class="celdaVacia"></div>
						<div class="casillaPartido" data-id="cuartos4"></div>
						<div class="celdaVacia"></div>
					</div>
					<!-- Octavos derecha -->
					<div class="columnaTorneo">
						<div class="casillaPartido" data-id="octavos5"></div>
						<div class="celdaVacia"></div>
						<div class="casillaPartido" data-id="octavos6"></div>
						<div class="celdaVacia"></div>
						<div class="casillaPartido" data-id="octavos7"></div>
						<div class="celdaVacia"></div>
						<div class="casillaPartido" data-id="octavos8"></div>
					</div>
				</div>
				<div class="separadorVisual"></div>
				<!-- Tablas (Equipos y Goleadores) -->
				<div class="contenedorTablas">
					<!-- Se puede rellenar dinámicamente más adelante -->
				</div>
			</div>
			<div class="contenedorTablas">
				<!-- Tabla Goleadores -->
				<div class="tablaGoleadores" id="tablaGoleadores">
					<h3>Goleadores</h3>
					<input type="text" id="buscarGoleadores"
						placeholder="Buscar jugador..."
						onkeyup="filtrarTabla('goleadores')">
					<table id="tablaGoleadoresBody">
						<thead>
							<tr>
								<th>Jugador</th>
								<th>Club</th>
								<th>Goles</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>

				<!-- Tabla Disciplina -->
				<div class="tablaGoleadores" id="tablaDisciplina">
					<h3>Disciplina</h3>
					<input type="text" id="buscarDisciplina"
						placeholder="Buscar jugador..."
						onkeyup="filtrarTabla('disciplina')">
					<table id="tablaDisciplinaBody">
						<thead>
							<tr>
								<th>Jugador</th>
								<th>Club</th>
								<th>Amarillas</th>
								<th>Rojas</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
				</div>

				<!-- Tabla Equipos -->
				<div class="tablaEquipos" id="tablaEquipos">
					<h3>Equipos</h3>
					<input type="text" id="buscarEquipos"
						placeholder="Buscar equipo..." onkeyup="filtrarTabla('equipos')">
					<table id="tablaEquiposBody">
						<thead>
							<tr>
								<th>Nombre</th>
								<th>Abreviatura</th>
								<th>PJ</th>
								<th>G</th>
								<th>P</th>
								<th>GF</th>
								<th>GC</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
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
document.addEventListener("DOMContentLoaded", async function () {

    console.log("DOM cargado correctamente");

    const searchParams = new URLSearchParams(window.location.search);
    const torneo_id = '<%=torneoId%>';
    const contextPath = '<%=request.getContextPath()%>';

    if (!torneo_id) {
        console.error("No se encontró el torneoId en la URL");
        alert("No se pudo cargar el torneo porque no se detectó el ID.");
        return;
    }

    // ------------------ FILTRADO ------------------
    window.filtrarTabla = function(tipo) {
        var inputId = tipo === 'goleadores' ? 'buscarGoleadores'
                    : tipo === 'disciplina' ? 'buscarDisciplina'
                    : 'buscarEquipos';
        var tablaBodyId = tipo === 'goleadores' ? 'tablaGoleadoresBody'
                        : tipo === 'disciplina' ? 'tablaDisciplinaBody'
                        : 'tablaEquiposBody';

        var input = document.getElementById(inputId).value.toLowerCase();
        var tbody = document.querySelector('#' + tablaBodyId + ' tbody');
        if(!tbody) return;

        Array.from(tbody.querySelectorAll('tr')).forEach(function(fila){
            var mostrar = Array.from(fila.cells).some(function(td){
                return td.textContent.toLowerCase().indexOf(input) > -1;
            });
            fila.style.display = mostrar ? '' : 'none';
        });
    };

    // ------------------ PAGINACION ------------------
    function paginarTabla(tipo, filasPorPagina) {
        filasPorPagina = filasPorPagina || 8;

        var tablaBodyId = tipo === 'goleadores' ? 'tablaGoleadoresBody'
                         : tipo === 'disciplina' ? 'tablaDisciplinaBody'
                         : 'tablaEquiposBody';

        var tbody = document.querySelector('#' + tablaBodyId + ' tbody');
        if(!tbody) return;

        var filas = Array.from(tbody.querySelectorAll('tr'));
        var paginaActual = 1;
        var totalPaginas = Math.ceil(filas.length / filasPorPagina);

        var pagDiv = document.querySelector('#' + tablaBodyId + '-paginacion');
        if(!pagDiv) {
            pagDiv = document.createElement('div');
            pagDiv.id = tablaBodyId + '-paginacion';
            pagDiv.style.display = 'flex';
            pagDiv.style.justifyContent = 'center';
            pagDiv.style.alignItems = 'center';
            pagDiv.style.flexWrap = 'nowrap';  
            pagDiv.style.gap = '0.5rem';
            pagDiv.style.marginTop = '10px';
            tbody.parentElement.appendChild(pagDiv);
        }

        function mostrarPagina(pagina) {
            var inicio = (pagina-1) * filasPorPagina;
            var fin = inicio + filasPorPagina;
            filas.forEach(function(fila, i){
                fila.style.display = (i>=inicio && i<fin) ? '' : 'none';
            });

            pagDiv.innerHTML = '';

            var btnAnterior = document.createElement('button');
            btnAnterior.textContent = 'Anterior';
            btnAnterior.className = 'paginacion'; 
            btnAnterior.disabled = (pagina === 1);
            btnAnterior.onclick = function(){ paginaActual--; mostrarPagina(paginaActual); };

            var btnSiguiente = document.createElement('button');
            btnSiguiente.textContent = 'Siguiente';
            btnSiguiente.className = 'paginacion'; 
            btnSiguiente.disabled = (pagina === totalPaginas);
            btnSiguiente.onclick = function(){ paginaActual++; mostrarPagina(paginaActual); };

            var spanInfo = document.createElement('span');
            spanInfo.style.whiteSpace = 'nowrap'; 
	
            spanInfo.textContent = ' Página ' + pagina + ' de ' + totalPaginas + ' ';

            pagDiv.appendChild(btnAnterior);
            pagDiv.appendChild(spanInfo);
            pagDiv.appendChild(btnSiguiente);
        }

        mostrarPagina(paginaActual);
    }

    // ------------------ CARGAR BRACKET ------------------
    async function cargarBracket() {
        try {
            var resp = await fetch(contextPath + '/torneo/bracket?torneoId=' + torneo_id);
            var data = await resp.json();

            var nombreTorneoElem = document.getElementById("nombreTorneo");
            if(nombreTorneoElem) nombreTorneoElem.textContent = data.torneo ? data.torneo.nombreTorneo : "Torneo";

            var rondas = [
                { nombre: "octavos", cantidad: 8 },
                { nombre: "cuartos", cantidad: 4 },
                { nombre: "semifinal", cantidad: 2 },
                { nombre: "partidoFinal", cantidad: 1 },
                { nombre: "tercerpuesto", cantidad: 1 }
            ];

            var nombresRondaMap = {
                octavos: "Octavos",
                cuartos: "Cuartos",
                semifinal: "Semifinal",
                partidoFinal: "Final",
                tercerpuesto: "Tercer Puesto"
            };

            rondas.forEach(function(r){
                var partidos = data[r.nombre] || [];
                var divsRonda = document.querySelectorAll(".casillaPartido[data-id^='" + r.nombre + "']");
                divsRonda.forEach(function(div,index){
                    var partido = partidos.find(function(p){ return p.ubicacionRonda === index+1; });
                    if(!partido){
                        div.innerHTML = '<em>' + nombresRondaMap[r.nombre] + '</em>';
                        return;
                    }

                    var contenidoRonda = '';
                    if(partido.actaCerrada){
                        contenidoRonda = '<div style="display:flex; flex-direction:column; justify-content:center; align-items:center; ">' +
                                            '<div style=" font-weight:bold;">' + (partido.clubLocalNombre || '-') + '</div>' +
                                            '<div style=" color:#ff0; font-weight:bold;">' + (partido.golesLocal != null ? partido.golesLocal : '-') + '-' + (partido.golesVisitante != null ? partido.golesVisitante : '-') + '</div>' +
                                            '<div style=" font-weight:bold;">' + (partido.clubVisitanteNombre || '-') + '</div>' +
                                        '</div>';
                    } else {
                        contenidoRonda = '<div style="display:flex; flex-direction:column; justify-content:center; align-items:center; line-height:1.1vw;">' +
                                            '<div style=" font-weight:bold;">' + (partido.clubLocalNombre || '-') + '</div>' +
                                            '<div style=" font-size:0.8em; color:#555;">' + (partido.fechaPartido || '-') + '</div>' +
                                            '<div style=" font-weight:bold;">' + (partido.clubVisitanteNombre || '-') + '</div>' +
                                        '</div>';
                    }

                    div.innerHTML = '<button class="btnPartido" ' +
                                    'style="width:100%; height:100%; border:none; background:none; color:white; display:flex; flex-direction:column; justify-content:center; align-items:center;' +
                                    (partido.actaCerrada ? ' cursor:not-allowed;' : ' cursor:pointer;') + '" ' +
                                    (partido.actaCerrada ? 'disabled' : '') +
                                    ' data-partido-id="' + partido.idPartidoTorneo + '">' +
                                    contenidoRonda +
                                    '</button>';
                });
            });

            await generarRondasAutomaticas(data);

        } catch(err){
            console.error("Error al cargar el bracket:", err);
            alert("No se pudo cargar el bracket.");
        }
    }

    async function generarRondasAutomaticas(data){
        var rondas = ["octavos","cuartos","semifinal"];
        for(var i=0; i<rondas.length; i++){
            var partidos = data[rondas[i]] || [];
            if(partidos.length > 0 && partidos.every(function(p){ return p.estado === "finalizado"; })){
                await generarSiguienteRonda(partidos);
            }
        }
    }

    async function generarSiguienteRonda(partidos){
        for(var i=0; i<partidos.length; i++){
            var p = partidos[i];
            try{
                await fetch(contextPath + '/torneo/bracket', {
                    method:'POST',
                    headers:{'Content-Type':'application/x-www-form-urlencoded'},
                    body: 'partidoId=' + p.idPartidoTorneo + '&ganadorId=' + p.equipoGanadorId
                });
            }catch(err){
                console.error("Error generando siguiente ronda:", err);
            }
        }
    }

    // ------------------ CLICK EN PARTIDOS ------------------
   document.addEventListener("click", function(e){
    var boton = e.target.closest(".btnPartido");
    if(!boton) return;

    var partidoId = boton.getAttribute("data-partido-id");

    // Guardamos el partido en sesión via POST
    fetch(contextPath + '/instalacion/actaPartido', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: '{"partidoIdSeleccionado":' + partidoId + '}'
    }).then(function(resp){
        if(resp.ok){
            // Redirigimos a la página del acta sin parámetros en la URL
            window.location.href = contextPath + '/instalacion/actaPartido';
        } else {
            console.error("Error al guardar el partido en sesión");
            alert("No se pudo seleccionar el partido");
        }
    }).catch(function(err){
        console.error("Error al guardar el partido en sesión:", err);
        alert("Ocurrió un error al seleccionar el partido");
    });
});


    // ------------------ CARGAR TABLAS ------------------
    async function cargarTablas(torneoId){
        try{
            var resp = await fetch(contextPath + '/jugadorEstadisticaTorneo');
            var datos = await resp.json();
            var torneoDatos = datos.filter(function(d){ return d.torneoId == torneoId; });

            // Goleadores
            var tbodyG = document.querySelector('#tablaGoleadoresBody tbody');
            if(tbodyG){
                tbodyG.innerHTML = '';
                torneoDatos.filter(function(d){ return d.golesTorneo>0; })
                           .sort(function(a,b){ return b.golesTorneo - a.golesTorneo; })
                           .forEach(function(d){
                               tbodyG.innerHTML += '<tr><td>' + (d.nombreJugador || ('Jugador '+d.jugadorId)) + '</td><td>' + (d.nombreClub || 'Sin club') + '</td><td>' + d.golesTorneo + '</td></tr>';
                           });
                paginarTabla('goleadores');
            }

            // Disciplina
            var tbodyD = document.querySelector('#tablaDisciplinaBody tbody');
            if(tbodyD){
                tbodyD.innerHTML = '';
                torneoDatos.filter(function(d){ return d.amarillasTorneo>0 || d.rojasTorneo>0; })
                           .forEach(function(d){
                               tbodyD.innerHTML += '<tr><td>' + (d.nombreJugador || ('Jugador '+d.jugadorId)) + '</td><td>' + (d.nombreClub || 'Sin club') + '</td><td>' + d.amarillasTorneo + '</td><td>' + d.rojasTorneo + '</td></tr>';
                           });
                paginarTabla('disciplina');
            }

            // Clubes
            var tbodyE = document.querySelector('#tablaEquiposBody tbody');
            if(tbodyE){
                tbodyE.innerHTML = '';
                var respC = await fetch(contextPath + '/clubEstadisticaTorneo');
                var datosC = await respC.json();
                var torneoClubs = datosC.filter(function(d){ return d.torneoId==torneoId; });
                torneoClubs.forEach(function(c){
                    tbodyE.innerHTML += '<tr><td>'+c.nombreClub+'</td><td>'+c.abreviaturaClub+'</td><td>'+c.partidosJugados+'</td><td>'+c.ganados+'</td><td>'+c.empatados+'</td><td>'+c.golesFavor+'</td><td>'+c.golesContra+'</td></tr>';
                });
                paginarTabla('equipos');
            }

        }catch(err){ console.error(err); }
    }

    // ------------------ INIT ------------------
    cargarBracket();
    cargarTablas(torneo_id);

});
</script>



	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		crossorigin="anonymous"></script>

</body>
</html>