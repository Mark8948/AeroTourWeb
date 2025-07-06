--
-- PostgreSQL database dump
--

-- Dumped from database version 17.5
-- Dumped by pg_dump version 17.5

-- Started on 2025-07-07 01:52:30

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4844 (class 0 OID 24835)
-- Dependencies: 218
-- Data for Name: airplanes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.airplanes (id, build_year, description, image, model_name, price) VALUES (1, 2000, 'Luxor', 24918, 'Luxor', 100);
INSERT INTO public.airplanes (id, build_year, description, image, model_name, price) VALUES (3, 2001, 'Gold', 24920, 'Gold', 2000);
INSERT INTO public.airplanes (id, build_year, description, image, model_name, price) VALUES (4, 1950, 'Mammoth Dodo', 24921, 'Mammoth Dodo', 300);


--
-- TOC entry 4843 (class 0 OID 24828)
-- Dependencies: 217
-- Data for Name: airplane_customization; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.airplane_customization (id, description, modification_first_availability_date, modification_name, modification_price, airplane_id) VALUES (1, 'Hot Seats', '2001-12-21', 'Hot Seats', 20, 1);
INSERT INTO public.airplane_customization (id, description, modification_first_availability_date, modification_name, modification_price, airplane_id) VALUES (8, 'Silver', '2001-12-12', 'Silver', 1500, 3);
INSERT INTO public.airplane_customization (id, description, modification_first_availability_date, modification_name, modification_price, airplane_id) VALUES (9, 'Bronze', '2123-12-31', 'Bronze', 1200, 3);
INSERT INTO public.airplane_customization (id, description, modification_first_availability_date, modification_name, modification_price, airplane_id) VALUES (10, 'Wheels', '1960-02-11', 'Wheels', 200, 4);
INSERT INTO public.airplane_customization (id, description, modification_first_availability_date, modification_name, modification_price, airplane_id) VALUES (4, 'Control', '2001-12-21', 'Control', 200, 1);
INSERT INTO public.airplane_customization (id, description, modification_first_availability_date, modification_name, modification_price, airplane_id) VALUES (2, 'Engine Up', '2001-12-21', 'Engine Up', 200, 1);
INSERT INTO public.airplane_customization (id, description, modification_first_availability_date, modification_name, modification_price, airplane_id) VALUES (3, 'Speed', '2001-12-21', 'Speed', 300, 1);


--
-- TOC entry 4847 (class 0 OID 24857)
-- Dependencies: 221
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users (id, email, name, surname) VALUES (1, 'a@a.a', 'a', 'a');
INSERT INTO public.users (id, email, name, surname) VALUES (2, 'b@b.b', 'b', 'b');
INSERT INTO public.users (id, email, name, surname) VALUES (3, 'c@c.c', 'c', 'c');


--
-- TOC entry 4845 (class 0 OID 24842)
-- Dependencies: 219
-- Data for Name: credentials; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.credentials (id, email, password, role, username, user_id) VALUES (2, 'b@b.b', '$2a$10$cIghqndOHlk5E7NPMXeRfeG/gQIM.POnNl07QnXL.AYOqmqV0nxBm', 'USER', 'b', 2);
INSERT INTO public.credentials (id, email, password, role, username, user_id) VALUES (1, 'a@a.a', '$2a$10$MDBqQhbZ89f7ZjpCSDFlLeM2Ppy8SP24Z3Mpvp32zDdp6Kyqh4x8q', 'SERVER_ADMINISTRATOR', 'a', 1);
INSERT INTO public.credentials (id, email, password, role, username, user_id) VALUES (3, 'c@c.c', '$2a$10$YEREhTv1Dh.dCfyDjfFISeo924gKeLFTV2KE5BrfFhoHZTPlMjAPK', 'USER', 'c', 3);


--
-- TOC entry 4854 (class 0 OID 24935)
-- Dependencies: 228
-- Data for Name: order_request; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.order_request (id, creation_date, stato, total_price, airplane_id, user_id) VALUES (202, '2025-07-01 06:22:44.075162', 'IN_ATTESA_DI_CONFERMA', 820, 1, 2);
INSERT INTO public.order_request (id, creation_date, stato, total_price, airplane_id, user_id) VALUES (252, '2025-07-01 06:48:21.330484', 'IN_ATTESA_DI_CONFERMA', 3500, 3, 2);
INSERT INTO public.order_request (id, creation_date, stato, total_price, airplane_id, user_id) VALUES (303, '2025-07-01 07:05:43.85207', 'IN_ATTESA_DI_CONFERMA', 500, 4, 2);
INSERT INTO public.order_request (id, creation_date, stato, total_price, airplane_id, user_id) VALUES (302, '2025-07-01 07:05:31.881602', 'ANNULLATO', 300, 4, 2);


--
-- TOC entry 4855 (class 0 OID 24956)
-- Dependencies: 229
-- Data for Name: order_customization; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.order_customization (order_id, customization_id) VALUES (202, 1);
INSERT INTO public.order_customization (order_id, customization_id) VALUES (202, 4);
INSERT INTO public.order_customization (order_id, customization_id) VALUES (202, 2);
INSERT INTO public.order_customization (order_id, customization_id) VALUES (202, 3);
INSERT INTO public.order_customization (order_id, customization_id) VALUES (252, 8);
INSERT INTO public.order_customization (order_id, customization_id) VALUES (303, 10);


--
-- TOC entry 4848 (class 0 OID 24864)
-- Dependencies: 222
-- Data for Name: visits_booking; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.visits_booking (id, guide_phone, guide_surname, status, visit_date_time, airplane_id, user_id) VALUES (1, NULL, NULL, 'ANNULLATO', NULL, 1, 2);
INSERT INTO public.visits_booking (id, guide_phone, guide_surname, status, visit_date_time, airplane_id, user_id) VALUES (2, NULL, NULL, 'RIFIUTATO', NULL, 3, 2);
INSERT INTO public.visits_booking (id, guide_phone, guide_surname, status, visit_date_time, airplane_id, user_id) VALUES (3, '333333333', 'Matt', 'CONFERMATO', '2002-12-12 12:00:00', 4, 2);


--
-- TOC entry 4861 (class 0 OID 0)
-- Dependencies: 223
-- Name: airplane_customization_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.airplane_customization_seq', 51, true);


--
-- TOC entry 4862 (class 0 OID 0)
-- Dependencies: 224
-- Name: airplanes_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.airplanes_seq', 51, true);


--
-- TOC entry 4863 (class 0 OID 0)
-- Dependencies: 225
-- Name: credentials_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.credentials_seq', 51, true);


--
-- TOC entry 4864 (class 0 OID 0)
-- Dependencies: 226
-- Name: order_request_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.order_request_seq', 351, true);


--
-- TOC entry 4865 (class 0 OID 0)
-- Dependencies: 220
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 3, true);


--
-- TOC entry 4866 (class 0 OID 0)
-- Dependencies: 227
-- Name: visits_booking_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.visits_booking_seq', 51, true);


-- Completed on 2025-07-07 01:52:30

--
-- PostgreSQL database dump complete
--

