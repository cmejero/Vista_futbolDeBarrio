<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Estilos CSS -->
<link rel="stylesheet" href="Css/Estilo.css">

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">

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

<title>REGISTRAR</title>
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
										<div class="col-sm-2 col-md-2 cabeceraArriba "
											style="justify-content: left;">
											<a href="">
												<button type="button" class="botonCabeceraContactar"
													onclick="abrirGmail()">CONTACTAR</button>
											</a>
										</div>
										<div class="col-sm-2 col-md-2 cabeceraArriba">
											<a href=""> <svg xmlns="http://www.w3.org/2000/svg"
													width="1.4vw" height="1.2vw" fill="currentColor"
													style="margin-right: 3px; color: white;" bi bi-instagram
													viewBox="0 0 16 16">
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
										<div class="col-sm-2 col-md-2 cabeceraMedio">
											<a href="Registrar.jsp">
	<a href="">
												<button type="button" class=" botonRegistrarCabecera"
													style="background-color: #d4af37;">REGISTRARSE</button>
																					</a>
										</div>
										<div class="col-sm-2 col-md-2 cabeceraMedio"
											style="text-decoration: underline;">
											<a href="InicioSesion.jsp" class="letraCabeceraMedio">INICIAR
												SESION</a>
										</div>
										<div class="col-sm-2 col-md-2 cabeceraMedio"
											style="text-decoration: underline;">
											<a href="" class="letraCabeceraMedio">¿QUIENES SOMOS?</a>
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
								<div class="col-sm-2 col-md-2 ">
									<div class="row  ">
										<div class="col-sm-1 col-md-1 cabeceraAbajo  "></div>
										<div class="col-sm-11 col-md-11 cabeceraAbajo ">
											<a href="Index.jsp" class="letraCabeceraAbajo" >INICIO</a>
										</div>

									</div>
								</div>

								<div class="col-sm-4 col-md-4 ">
									<div class="row  ">
										<div class="col-sm-6 col-md-6 cabeceraAbajo ">
											<a href="" class="letraCabeceraAbajo">EVENTOS</a>
										</div>

										<div class="col-sm-6 col-md-6 cabeceraAbajo ">
											<a href="" class="letraCabeceraAbajo seccion-bloqueada">ALQUILERES<span class="tooltip-text">Sección en
													desarrollo</span></a>
										</div>
									</div>
								</div>

								<div class="col-sm-4 col-md-4 ">
									<div class="row  ">
										<div class="col-sm-6 col-md-6 cabeceraAbajo ">
											<a href="" class="letraCabeceraAbajo">MARCADORES</a>
										</div>
										<div
											class="col-sm-6 col-md-6 cabeceraAbajo  ">
											<a href="" class="letraCabeceraAbajo seccion-bloqueada">DESAFIOS<span class="tooltip-text">Sección en
													desarrollo</span></a>
										</div>

									</div>
								</div>

								<!-- columna derecha -->
								<div class="col-sm-2 col-md-2 ">

									<div class="row">

										<div class="col-sm-12 col-md-12 cabeceraAbajo">
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
													<li><a class="dropdown-item seccion-bloqueada" href="#"
														style="color: white; background-color: #005500;">Idioma<span class="tooltip-text">Sección en
													desarrollo</span>
													</a></li>
													<li><a class="dropdown-item seccion-bloqueada" href="#"
														style="color: white;">Ayuda <span class="tooltip-text">Sección en
													desarrollo</span></a></li>
													<li><a class="dropdown-item seccion-bloqueada" href="#"
														style="color: white;">Configuración <span class="tooltip-text">Sección en
													desarrollo</span></a></li>

												</ul>
											</div>
										</div>
										<div class="col-sm-1 col-md-1 cabeceraAbajo "></div>
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
											<a href="Registrar.jsp">
												<button type="button" class="botonRegistrarCabecera" style="background-color: #d4af37; font-size:1.6vw">REGISTRARSE</button>
											</a>
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
									<a href="Jugador.jsp" class="letraCabeceraAbajo "
										style="text-decoration: none; font-size: 2.5vw; ">INICIO</a>
								</div>
								<div
									class="col-4 d-flex justify-content-start align-items-center ps-3 ">
									<a href="MarcadoresJugador.jsp" class="letraCabeceraAbajo "
										style="text-decoration: none; font-size: 2.5vw;">MARCADORES</a>
								</div>
								<div
									class="col-3 d-flex justify-content-start align-items-center ps-3 ">
									<a href="MiClubJugador.jsp" class="letraCabeceraAbajo "
										style="text-decoration: none; font-size: 2.5vw;">EVENTOS</a>
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


											<li><a class="dropdown-item " href="InicioSesion.jsp"
												style="color: white;">Iniciar sesión </a></li>
											<li>
												<hr class="dropdown-divider" style="border-color: #006600;">
											</li>

											<li><a class="dropdown-item seccion-bloqueada" href="">Alquileres
													<span class="tooltip-text">Sección en desarrollo</span>
											</a></li>
											<li><a class="dropdown-item seccion-bloqueada" href="">Desafios<span
													class="tooltip-text">Sección en desarrollo</span>
											</a></li>
											<li><a class="dropdown-item " href="#"
												style="color: white;">¿Quienes somos? </a></li>


											<li>
												<hr class="dropdown-divider" style="border-color: #006600;">
											</li>
											<li><a class="dropdown-item seccion-bloqueada" href="#"
												style="color: white;">Idioma <span class="tooltip-text">Sección
														en desarrollo</span></a></li>
											<li><a class="dropdown-item seccion-bloqueada" href="#"
												style="color: white;">Ayuda <span class="tooltip-text">Sección
														en desarrollo</span></a></li>
											<li><a class="dropdown-item seccion-bloqueada" href="#"
												style="color: white;">Configuración <span
													class="tooltip-text">Sección en desarrollo</span></a></li>



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
		<!-- FILA BOTONES -->
		<div class="container-fluid mt-3 "
			style="display: flex; justify-content: center;">


			<div class="row ">



				<div
					class="col-md-12 col-sm-12 d-sm-block d-md-block d-none columnaBotonRegistrar mb-3">

					<button type="button" class=" botonSeleccion p-2 pt-3 pb-3"
						id="mostrarUsuarioBtn"
						style="box-shadow: 3.5px 3.5px 0px red, 3.5px 3.5px 8px rgba(255, 0, 0, 0.5);">REGISTRARSE
						COMO USUARIO</button>

					<button type="button" class=" botonSeleccion p-2 pt-3 pb-3"
						id="mostrarInstalacionBtn"
						style="box-shadow: 3.5px 3.5px 0px green, 3.5px 3.5px 8px rgba(0, 128, 0, 0.5);">REGISTRARSE
						COMO INSTALACIÓN</button>

					<button type="button" class=" botonSeleccion p-2 pt-3 pb-3 "
						id="mostrarClubBtn"
						style="box-shadow: 3.5px 3.5px 0px blue, 3.5px 3.5px 8px rgba(0, 0, 255, 0.5);">REGISTRARSE
						COMO CLUB</button>


					



				</div>

				<div
					class="d-md-none d-sm-none col-12 d-block columnaBotonRegistrar ">

					<button type="button" class="botonSeleccionX p-2"
						id="mostrarUsuarioX"
						style="box-shadow: 3.5px 3.5px 0px red, 3.5px 3.5px 8px rgba(255, 0, 0, 0.5);">REGISTRARSE
						COMO USUARIO</button>
					<button type="button" class="botonSeleccionX p-2"
						id="mostrarInstalacionBtnX"
						style="box-shadow: 3.5px 3.5px 0px green, 3.5px 3.5px 8px rgba(0, 128, 0, 0.5);">REGISTRARSE
						COMO INSTALACIÓN</button>
					<button type="button" class="botonSeleccionX p-2"
						id="mostrarClubBtnX"
						style="box-shadow: 3.5px 3.5px 0px blue, 3.5px 3.5px 8px rgba(0, 0, 255, 0.5);">REGISTRARSE
						COMO CLUB</button>
					



				</div>



			</div>
		</div>

		<div class="container-fluid  ">
			<!-- FORMULARIO USUARIO -->
			<div class="row mt-3 mb-5" style="display: none;"
				id="usuarioContainer">
				<div class="col-md-10 col-sm-10 mx-auto"
					style="background-color: #dfead5; border-radius: 12px">
					<h2 class="text-center mt-4 mb-5 pt-4"
						style="font-size: 3.5vw; text-decoration: underline;">
						<i>Formulario de Registro: Usuario</i>
					</h2>
					<div class="registrarFormulario">
						<form action="usuario" method="POST" enctype="multipart/form-data"
							onsubmit="return validarFormulario()">
							<input type="hidden" name="accion" value="aniadir">
							<table class="tablaFormulario"
								style="background-color: #dedede; color: black; box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.6), 0px -4px 8px rgba(0, 0, 0, 0.15)">
								<tbody>
									<tr>
										<td><label for="nombreCompletoUsuario"
											class="formularioLabel">Nombre Completo</label> <input
											type="text" class="form-control" id="nombreCompletoUsuario"
											name="nombreCompletoUsuario"
											style="font-size: 1vw; border: 1px solid #818181; height: 2.5vw;"
											pattern="[A-Za-zÁÉÍÓÚáéíóúÑñ ]{2,50}"
											title="Solo letras y espacios, mínimo 2 caracteres" required>
										</td>
										<td><label for="aliasUsuario" class="formularioLabel">Alias</label>
											<input type="text" class="form-control" id="aliasUsuario"
											style="font-size: 1vw; border: 1px solid #818181; height: 2.5vw;"
											name="aliasUsuario" required></td>
									</tr>
									<tr>
										<td><label for="fechaNacimientoUsuario"
											class="formularioLabel">Fecha de Nacimiento</label> <input
											type="date"
											style="font-size: 1vw; border: 1px solid #818181; height: 2.5vw;"
											class="form-control" id="fechaNacimientoUsuario"
											name="fechaNacimientoUsuario"
											max="<%=new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date())%>"
											required></td>
										<td><label for="emailUsuario" class="formularioLabel">Correo
												Electrónico</label> <input type="email" class="form-control"
											style="font-size: 1vw; border: 1px solid #818181; height: 2.5vw;"
											id="emailUsuario" name="emailUsuario" required></td>
									</tr>
									<tr>
										<td><label for="telefonoUsuario" class="formularioLabel">Teléfono</label>
											<input type="tel" class="form-control" id="telefonoUsuario"
											name="telefonoUsuario" pattern="[0-9]{9}"
											style="font-size: 1vw; border: 1px solid #818181; height: 2.5vw;"
											title="Debe tener exactamente 9 dígitos" required></td>
										<td><label for="rolUsuario" class="formularioLabel">Rol
												usuario</label> <select class="form-select" id="rolUsuario"
											style="font-size: 1vw; border: 1px solid #818181; height: 2.5vw;"
											name="rolUsuario" required>
												<option value="Jugador">Jugador</option>

										</select></td>
									</tr>
									<tr>
										<td><label for="passwordUsuario" class="formularioLabel">Contraseña</label>
											<input type="password" class="form-control"
											style="font-size: 1vw; border: 1px solid #818181; height: 2.5vw;"
											id="passwordUsuario" name="passwordUsuario" minlength="8"
											title="Mínimo 8 caracteres" required></td>
										<td><label for="passwordUsuario2" class="formularioLabel">Repetir
												contraseña</label> <input type="password" class="form-control"
											style="font-size: 1vw; border: 1px solid #818181; height: 2.5vw;"
											id="passwordUsuario2" name="passwordUsuario2" minlength="8"
											required></td>
									</tr>
									<tr>
										<td><label for="imagenUsuario" class="formularioLabel">Imagen
												del Usuario</label> <input type="file" class="form-control"
											style="font-size: 1vw; border: 1px solid #818181; height: 2.5vw;"
											id="imagenUsuario" name="imagenUsuario"></td>
										<td><label for="descripcionUsuario"
											class="formularioLabel">Descripción</label> <textarea
												style="font-size: 1vw; border: 1px solid #818181; height: 2.5vw;"
												class="form-control" id="descripcionUsuario"
												name="descripcionUsuario" rows="3"></textarea></td>
									</tr>
								</tbody>
							</table>
							<div class="text-center mt-4 mb-5">
								<button type="submit" class="botonRegistrarCabecera">REGISTRARSE</button>
							</div>
						</form>
					</div>
				</div>
			</div>




			<!-- FORMULARIO CLUB -->
			<div class="row mt-3 mb-5" style="display: none;" id="clubContainer">
				<div class="col-md-10 col-sm-10 mx-auto"
					style="background-color: #dfead5; border-radius: 12px">
					<h2 class="text-center mt-4 mb-5 pt-4"
						style="font-size: 3.5vw; text-decoration: underline;">
						<i>Formulario de Registro: Club</i>
					</h2>
					<div class="registrarFormulario">
						<form action="club" method="POST" enctype="multipart/form-data"
							onsubmit="return validarFormulario()">
							<input type="hidden" name="accion" value="aniadir">
							<table class="tablaFormulario"
								style="background-color: #d8d8d8; color: black; box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.6), 0px -4px 8px rgba(0, 0, 0, 0.15)">
								<tbody>
									<tr>
										<td><label for="nombreClub" class="formularioLabel">Nombre
												del Club</label> <input type="text"
											style="font-size: 1vw; border: 1px solid #818181; height: 2.5vw;"
											class="form-control" id="nombreClub" name="nombreClub"
											required pattern="^[A-Za-zÁáÉéÍíÓóÚúÑñ ]+$"
											title="El nombre no puede contener números ni caracteres especiales"></td>

										<td><label for="abreviaturaClub" class="formularioLabel">Abreviatura</label>
											<input type="text"
											style="font-size: 1vw; border: 1px solid #818181; height: 2.5vw;"
											class="form-control" id="abreviaturaClub"
											name="abreviaturaClub" required
											pattern="^[A-Za-zÁáÉéÍíÓóÚúÑñ]+$"
											title="La abreviatura no puede contener números ni caracteres especiales"
											maxlength="3" oninput="this.value = this.value.toUpperCase()"></td>
									</tr>
									<tr>
										<td><label for="emailClub" class="formularioLabel">Correo
												Electrónico</label> <input type="email"
											style="font-size: 1vw; border: 1px solid #818181; height: 2.5vw;"
											class="form-control" id="emailClub" name="emailClub" required></td>

										<td><label for="telefonoClub" class="formularioLabel">Teléfono</label>
											<input type="tel"
											style="font-size: 1vw; border: 1px solid #818181; height: 2.5vw;"
											class="form-control" id="telefonoClub" name="telefonoClub"
											required pattern="^\+?[0-9]{9,15}$"
											title="El teléfono debe tener entre 9 y 15 dígitos"></td>
									</tr>
									<tr>
										<td><label for="paisClub" class="formularioLabel">País</label>
											<input type="text"
											style="font-size: 1vw; border: 1px solid #818181; height: 2.5vw;"
											class="form-control" id="paisClub" name="paisClub" required
											pattern="^[A-Za-zÁáÉéÍíÓóÚúÑñ ]+$"
											title="El país no puede contener números ni caracteres especiales"></td>

										<td><label for="localidadClub" class="formularioLabel">Localidad</label>
											<input type="text"
											style="font-size: 1vw; border: 1px solid #818181; height: 2.5vw;"
											class="form-control" id="localidadClub" name="localidadClub"
											required pattern="^[A-Za-zÁáÉéÍíÓóÚúÑñ ]+$"
											title="La localidad no puede contener números ni caracteres especiales"></td>
									</tr>
									<tr>
										<td><label for="passwordClub" class="formularioLabel">Contraseña</label>
											<input type="password"
											style="font-size: 1vw; border: 1px solid #818181; height: 2.5vw;"
											class="form-control" id="passwordClub" name="passwordClub"
											required minlength="8"
											title="La contraseña debe tener al menos 8 caracteres"></td>

										<td><label for="repasswordClub" class="formularioLabel">Repetir
												Contraseña</label> <input type="password"
											style="font-size: 1vw; border: 1px solid #818181; height: 2.5vw;"
											class="form-control" id="repasswordClub"
											name="repasswordClub" required></td>
									</tr>
									<tr>
										<td><label for="logoClub" class="formularioLabel">Escudo
												del Club</label> <input type="file"
											style="font-size: 1vw; border: 1px solid #818181;"
											class="form-control" id="logoClub" name="logoClub"></td>

										<td><label for="descripcionClub" class="formularioLabel">Descripción</label>
											<textarea class="form-control"
												style="font-size: 1vw; border: 1px solid #818181; border-bottom: 1px solid #818181;"
												id="descripcionClub" name="descripcionClub" rows="3"></textarea></td>
									</tr>
								</tbody>
							</table>
							<div class="text-center mt-4 mb-5">
								<button type="submit" class="botonRegistrarCabecera ">REGISTRARSE</button>
							</div>
						</form>
					</div>
				</div>
			</div>


			<!-- FORMULARIO INSTALACIÓN -->
			<div class="row mt-3 mb-5" style="display: none;"
				id="instalacionContainer">
				<div class="col-md-10 col-sm-10 mx-auto"
					style="background-color: #dfead5; border-radius: 12px">
					<h2 class="text-center mt-4 mb-5 pt-4"
						style="font-size: 3.5vw; text-decoration: underline;">
						<i>Formulario de Registro: Instalación</i>
					</h2>
					<div class="registrarFormulario">
						<form action="instalacion" method="POST"
							enctype="multipart/form-data"
							onsubmit="return validarFormulario()">
							<input type="hidden" name="accion" value="aniadir">

							<table class="tablaFormulario"
								style="background-color: #d1d1d1; color: black; box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.6), 0px -4px 8px rgba(0, 0, 0, 0.15)">
								<tbody>
									<tr>
										<td><label for="nombreInstalacion"
											class="formularioLabel">Nombre de la Instalación</label> <input
											type="text" class="form-control" id="nombreInstalacion"
											name="nombreInstalacion" required
											style="font-size: 1vw; border: 1px solid #818181; height: 2.5vw;">
										</td>
										<td><label for="direccionInstalacion"
											class="formularioLabel">Dirección</label> <input type="text"
											class="form-control" id="direccionInstalacion"
											name="direccionInstalacion" required
											style="font-size: 1vw; border: 1px solid #818181; height: 2.5vw;">
										</td>
									</tr>
									<tr>
										<td><label for="emailInstalacion" class="formularioLabel">Correo
												Electrónico</label> <input type="email" class="form-control"
											id="emailInstalacion" name="emailInstalacion" required
											style="font-size: 1vw; border: 1px solid #818181; height: 2.5vw;"
											title="Introduce un correo electrónico válido."></td>
										<td><label for="telefonoInstalacion"
											class="formularioLabel">Teléfono</label> <input type="tel"
											class="form-control" id="telefonoInstalacion"
											name="telefonoInstalacion" required
											style="font-size: 1vw; border: 1px solid #818181; height: 2.5vw;"
											pattern="^\+?[0-9]{9,15}$"
											title="El teléfono debe tener entre 9 y 15 dígitos.">
										</td>
									</tr>
									<tr>
										<td><label for="passwordInstalacion"
											class="formularioLabel">Contraseña</label> <input
											type="password" class="form-control" id="passwordInstalacion"
											name="passwordInstalacion" required
											style="font-size: 1vw; border: 1px solid #818181; height: 2.5vw;"
											minlength="8"
											title="La contraseña debe tener al menos 8 caracteres.">
										</td>
										<td><label for="repasswordInstalacion"
											class="formularioLabel">Repetir Contraseña</label> <input
											type="password" class="form-control"
											id="repasswordInstalacion" name="repasswordInstalacion"
											required
											style="font-size: 1vw; border: 1px solid #818181; height: 2.5vw;">
										</td>
									</tr>
									<tr>
										<td><label for="tipoCampo1" class="formularioLabel">Pista
												deportiva principal</label> <select class="form-select"
											id="tipoCampo1" name="tipoCampo1" required
											style="font-size: 1vw; border: 1px solid #818181;">
												<option style="font-size: 1.2vw" value="Futbol5">Fútbol
													5</option>
												<option style="font-size: 1.2vw" value="Futbol7">Fútbol
													7</option>
												<option style="font-size: 1.2vw" value="Futbol11">Fútbol
													11</option>
										</select></td>
										<td><label for="tipoCampo2" class="formularioLabel">Pista
												deportiva secundaria</label> <select class="form-select"
											id="tipoCampo2" name="tipoCampo2"
											style="font-size: 1vw; border: 1px solid #818181;">
												<option style="font-size: 1.2vw" value="">Ninguno</option>
												<option style="font-size: 1.2vw" value="Futbol5">Fútbol
													5</option>
												<option style="font-size: 1.2vw" value="Futbol7">Fútbol
													7</option>
												<option style="font-size: 1.2vw" value="Futbol11">Fútbol
													11</option>
										</select></td>
									</tr>
									<tr>
										<td><label for="tipoCampo3" class="formularioLabel">Pista
												deportiva adicional</label> <select class="form-select"
											id="tipoCampo3" name="tipoCampo3"
											style="font-size: 1vw; border: 1px solid #818181;">
												<option value="">Ninguno</option>
												<option style="font-size: 1.2vw" value="Futbol5">Fútbol
													5</option>
												<option style="font-size: 1.2vw" value="Futbol7">Fútbol
													7</option>
												<option style="font-size: 1.2vw" value="Futbol11">Fútbol
													11</option>
										</select></td>
										<td><label for="estadoInstalacion"
											class="formularioLabel">Estado de la Instalación</label> <select
											class="form-select" id="estadoInstalacion"
											name="estadoInstalacion" required
											style="font-size: 1vw; border: 1px solid #818181;">
												<option style="font-size: 1.2vw" value="Activo">Activo</option>
												<option style="font-size: 1.2vw" value="Inactivo">Inactivo</option>
										</select></td>
									</tr>
									<tr>
										<td><label for="imagenInstalacion"
											class="formularioLabel">Imagen de la Instalación</label> <input
											type="file" class="form-control" id="imagenInstalacion"
											name="imagenInstalacion"
											style="font-size: 1vw; border: 1px solid #818181;"
											accept="image/*"></td>
										<td><label for="serviciosInstalacion"
											class="formularioLabel">Servicios</label> <textarea
												class="form-control" id="serviciosInstalacion"
												name="serviciosInstalacion" rows="3"
												style="font-size: 1vw; border: 1px solid #818181;"></textarea>
										</td>
									</tr>
								</tbody>
							</table>
							<div class="text-center mt-4 mb-5">
								<button type="submit" class="botonRegistrarCabecera">REGISTRARSE</button>
							</div>
						</form>
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
	document.addEventListener("DOMContentLoaded", function() {
	    // Botones de la versión para escritorio
	    const mostrarUsuarioBtn = document.getElementById('mostrarUsuarioBtn');
	    const mostrarInstalacionBtn = document.getElementById('mostrarInstalacionBtn');
	    const mostrarClubBtn = document.getElementById('mostrarClubBtn');


	    // Botones de la versión para móvil
	    const mostrarUsuarioX = document.getElementById('mostrarUsuarioX');
	    const mostrarInstalacionBtnX = document.getElementById('mostrarInstalacionBtnX');
	    const mostrarClubBtnX = document.getElementById('mostrarClubBtnX');
	   

	    // Contenedores de cada sección
	    const usuarioContainer = document.getElementById('usuarioContainer');
	    const instalacionContainer = document.getElementById('instalacionContainer');
	    const clubContainer = document.getElementById('clubContainer');
	   

	    // Función para mostrar el contenedor correspondiente
	    function mostrarContenedor(mostrar) {
	        usuarioContainer.style.display = "none";
	        instalacionContainer.style.display = "none";
	        clubContainer.style.display = "none";
	        
	        mostrar.style.display = "block";
	    }

	    // Función para activar el botón seleccionado
	    function activarBoton(boton) {
	        [mostrarUsuarioBtn, mostrarInstalacionBtn, mostrarClubBtn, ,
	         mostrarUsuarioX, mostrarInstalacionBtnX, mostrarClubBtnX, ].forEach(btn => {
	            btn.style.backgroundColor = "";
	            btn.style.color = "";
	        });
	        boton.style.backgroundColor = "#d4af37";
	        boton.style.color = "black";
	    }

	    // Eventos para los botones de escritorio
	    mostrarUsuarioBtn.addEventListener('click', function(event) {
	        event.preventDefault();
	        mostrarContenedor(usuarioContainer);
	        activarBoton(mostrarUsuarioBtn);
	    });

	    mostrarInstalacionBtn.addEventListener('click', function(event) {
	        event.preventDefault();
	        mostrarContenedor(instalacionContainer);
	        activarBoton(mostrarInstalacionBtn);
	    });

	    mostrarClubBtn.addEventListener('click', function(event) {
	        event.preventDefault();
	        mostrarContenedor(clubContainer);
	        activarBoton(mostrarClubBtn);
	    });

	   

	    // Eventos para los botones de móvil
	    mostrarUsuarioX.addEventListener('click', function(event) {
	        event.preventDefault();
	        mostrarContenedor(usuarioContainer);
	        activarBoton(mostrarUsuarioX);
	    });

	    mostrarInstalacionBtnX.addEventListener('click', function(event) {
	        event.preventDefault();
	        mostrarContenedor(instalacionContainer);
	        activarBoton(mostrarInstalacionBtnX);
	    });

	    mostrarClubBtnX.addEventListener('click', function(event) {
	        event.preventDefault();
	        mostrarContenedor(clubContainer);
	        activarBoton(mostrarClubBtnX);
	    });

	  

	    // Mostrar por defecto el contenedor de usuario
	    mostrarContenedor(usuarioContainer);
	    activarBoton(mostrarUsuarioBtn);

	    // Validación del formulario de usuario
	    const formularioUsuario = document.querySelector("#usuarioContainer form");

	    formularioUsuario.addEventListener("submit", function(event) {
	        event.preventDefault();
	        const formData = new FormData(formularioUsuario);
	        const accion = formData.get("accion");
	        if (accion) {
	            formularioUsuario.submit();
	        } else {
	            alert("Error: No se ha definido una acción válida en el formulario.");
	        }
	    });
	});
	
	  window.onload = function() {
	        var today = new Date();
	        var dd = String(today.getDate()).padStart(2, '0');
	        var mm = String(today.getMonth() + 1).padStart(2, '0');
	        var yyyy = today.getFullYear();
	        today = yyyy + '-' + mm + '-' + dd;

	        document.getElementById('fechaNacimientoUsuario').setAttribute('max', today);
	    }
	  
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