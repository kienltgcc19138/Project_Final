--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

-- Started on 2023-04-23 23:29:28 +07

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 214 (class 1259 OID 32770)
-- Name: course; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.course (
    course_id bigint NOT NULL,
    created_at timestamp without time zone,
    created_by character varying(255),
    description text,
    name character varying(255)
);


ALTER TABLE public.course OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 32850)
-- Name: course_generator; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.course_generator
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.course_generator OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 32777)
-- Name: event; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.event (
    event_id bigint NOT NULL,
    created_at timestamp without time zone,
    created_by character varying(255),
    description text,
    location character varying(255),
    name character varying(255),
    score real,
    status character varying(255),
    time_end timestamp without time zone,
    time_start timestamp without time zone,
    topic character varying(255)
);


ALTER TABLE public.event OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 32851)
-- Name: event_generator; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.event_generator
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.event_generator OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 32784)
-- Name: file; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.file (
    file_data_id bigint NOT NULL,
    created_at timestamp without time zone,
    created_by character varying(255),
    external_id character varying(255),
    file_name character varying(255),
    link character varying(255),
    type character varying(255),
    event_id bigint
);


ALTER TABLE public.file OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 32852)
-- Name: file_data_generator; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.file_data_generator
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.file_data_generator OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 32791)
-- Name: major; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.major (
    major_id bigint NOT NULL,
    created_at timestamp without time zone,
    created_by character varying(255),
    description text,
    name character varying(255)
);


ALTER TABLE public.major OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 32853)
-- Name: major_generator; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.major_generator
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.major_generator OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 32798)
-- Name: messages; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.messages (
    id character varying(255) NOT NULL,
    created_at timestamp without time zone,
    created_by character varying(255),
    description text,
    type character varying(255),
    users_id character varying(255)
);


ALTER TABLE public.messages OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 32805)
-- Name: notification; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.notification (
    notification_id bigint NOT NULL,
    created_at timestamp without time zone,
    description character varying(255),
    event_id bigint,
    is_read boolean NOT NULL,
    users_users_id character varying(255)
);


ALTER TABLE public.notification OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 32854)
-- Name: notification_generator; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.notification_generator
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.notification_generator OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 32812)
-- Name: recommend; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.recommend (
    recommend_id bigint NOT NULL,
    created_at timestamp without time zone,
    created_by character varying(255),
    description character varying(255),
    name character varying(255),
    users_users_id character varying(255)
);


ALTER TABLE public.recommend OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 32855)
-- Name: recommend_generator; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.recommend_generator
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.recommend_generator OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 32819)
-- Name: sequence_value_item; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sequence_value_item (
    seq_name character varying(255) NOT NULL,
    created_at timestamp without time zone,
    last_updated_stamp timestamp without time zone,
    last_updated_tx_stamp timestamp without time zone,
    seq_id integer
);


ALTER TABLE public.sequence_value_item OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 32824)
-- Name: ticket; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ticket (
    object_id character varying(255) NOT NULL,
    created_at timestamp without time zone,
    created_by character varying(255),
    description text,
    name character varying(255),
    object_type character varying(255),
    event_id bigint
);


ALTER TABLE public.ticket OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 32831)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    users_id character varying(255) NOT NULL,
    birthday timestamp without time zone,
    created_at timestamp without time zone,
    created_by character varying(255),
    email character varying(255),
    full_name character varying(255),
    gender character varying(255),
    password character varying(255),
    phone character varying(255),
    roles character varying(255),
    course_id bigint,
    major_id bigint
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 32838)
-- Name: users_event; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users_event (
    event_id bigint NOT NULL,
    users_id character varying(255) NOT NULL,
    created_at timestamp without time zone,
    is_join boolean,
    score real
);


ALTER TABLE public.users_event OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 32843)
-- Name: users_preference; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users_preference (
    users_preference_id character varying(255) NOT NULL,
    code character varying(255),
    created_at timestamp without time zone,
    type character varying(255),
    users_id character varying(255)
);


ALTER TABLE public.users_preference OWNER TO postgres;

--
-- TOC entry 3663 (class 0 OID 32770)
-- Dependencies: 214
-- Data for Name: course; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.course (course_id, created_at, created_by, description, name) FROM stdin;
1	2023-04-16 12:53:26.92	GCC19100	\N	K7
2	2023-04-19 17:12:53.305	GCC19100	\N	K8
3	2023-04-19 17:13:06.547	GCC19100	\N	K9
4	2023-04-19 17:13:17.655	GCC19100	\N	K10
5	2023-04-19 17:13:28.878	GCC19100	\N	K11
\.


--
-- TOC entry 3664 (class 0 OID 32777)
-- Dependencies: 215
-- Data for Name: event; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.event (event_id, created_at, created_by, description, location, name, score, status, time_end, time_start, topic) FROM stdin;
102	2023-04-19 18:22:28.193	GCC19100	<p>[CT] MEET GRAPHICS PROFESSOR AT GREENWICH UK</p><p>Link for registration: <a href="https://forms.office.com/r/yrYJgbL9br?fbclid=IwAR0elc0y9qB5vTa8_UTzedsk_iZlqoZyxdZfqqyXFhQwcoS4T9TyjWBmOAo" rel="noopener noreferrer" target="_blank" style="color: var(--blue-link);">https://forms.office.com/r/yrYJgbL9br</a></p><p>Professor Anastasios Maragiannis,Head of School of Design, University of Greenwich, will meet with students at the Greenwich Vietnam - Can Tho campus to discuss the following topic in order to inform and assist them in developing a more accurate and objective understanding of AI and the creative industry: "AI and the creative industry. Professor Anastasios’s career journey ". The Professor will provide to students the whole picture of technology and the future of the global graphic industry based on his professional adventure.</p><p>Information &amp; Occasions:</p><p>Audience: Graphic design students - Cantho campus.</p><p>Time: 02:30 PM, March 24, 2023</p><p>Venue: Hall 3A, Greenwich Vietnam - Cantho campus.</p>	Hall 3A, Greenwich Vietnam - Cantho campus	MEET GRAPHICS PROFESSOR AT GREENWICH UK	8	HAPPENING	2023-04-21 02:22:00	2023-04-18 00:22:00	Talk Show
2	2023-04-16 13:18:55.175	GCC19100	<p>AAA</p>	3A	Hi	12	HAPPENING	2023-04-18 20:18:00	2023-04-15 20:18:00	Hi
52	2023-04-19 14:53:17.71	GCC19100	<p><span style="color: var(--primary-text);"><img src="https://static.xx.fbcdn.net/images/emoji.php/v9/tdf/2/16/1f4a5.png" alt="💥" height="16" width="16"> </span>There are so many posts these days that you forgot that there is a very meaningful event to bring your soul to this happy experience, right??</p><p><span style="color: var(--primary-text);"><img src="https://static.xx.fbcdn.net/images/emoji.php/v9/t75/2/16/2728.png" alt="✨" height="16" width="16"></span>Information &amp; Events<span style="color: var(--primary-text);">:&nbsp;“TO BE A WELL-BEING PERSON”</span></p><p><span style="color: var(--primary-text);"><img src="https://static.xx.fbcdn.net/images/emoji.php/v9/t92/2/16/1f539.png" alt="🔹" height="16" width="16"></span><em style="color: var(--primary-text);">Time: 09h00, ngày 15/4/2023.</em></p><p><em style="color: var(--primary-text);"><img src="https://static.xx.fbcdn.net/images/emoji.php/v9/t92/2/16/1f539.png" alt="🔹" height="16" width="16">Location: </em>Hall 3A Greenwich Vietnam - Can Tho Campus</p><p><em style="color: var(--primary-text);"><img src="https://static.xx.fbcdn.net/images/emoji.php/v9/t92/2/16/1f539.png" alt="🔹" height="16" width="16"></em>Audience: Students of Greenwich Vietnam – Can Tho Campus.</p><p>Oh, the sand is slow to dig, guys, hurry up. I wish all of you participating in this event a happy and healthy birthday. Collecting the three ears of the East is blessed with many blessings. I wish you guys a lifetime of peace. Eternally, darlings, live peacefully in the light of the Buddhas of the ten directions. May Amitabha Buddha compassionately bless you for a lifetime of happiness. A Buddhist recitation to let sentient beings' minds become Buddha's minds. A Buddhist recitation to turn a cloudy pond into a clear pond. One sentence of reciting the Buddha's name to increase blessings and eliminate karma Namo Amitabha Buddha asked the Buddha to bless you with eternal peace, dear. Today is a good day, I would like to share PR's love with the little heart of my life, darlings, here's the link to register for this event: https://forms.gle/V25L4PkFCnuuQnQm8</p><p><br></p>	Hall 3A Greenwich Vietnam - Can Tho Campus	TO BE A WELL-BEING PERSON	10	UPCOMING	2023-04-22 00:30:00	2023-04-22 00:00:00	Talk Show
\.


--
-- TOC entry 3665 (class 0 OID 32784)
-- Dependencies: 216
-- Data for Name: file; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.file (file_data_id, created_at, created_by, external_id, file_name, link, type, event_id) FROM stdin;
2	2023-04-16 13:18:49.007	GCC19100	event-be/gnynus2me4kc9zr24i0m	332157447_703389661565463_8067485527831161166_n.jpg	http://res.cloudinary.com/dgn07k2h0/image/upload/v1681625928/event-be/gnynus2me4kc9zr24i0m.jpg	\N	2
52	2023-04-19 14:53:21.133	GCC19100	event-be/t7g34tagmaafdymxz6hx	1.jpeg	http://res.cloudinary.com/dgn07k2h0/image/upload/v1681890800/event-be/t7g34tagmaafdymxz6hx.jpg	\N	\N
53	2023-04-19 14:55:02.611	GCC19100	event-be/tol7vf1drrir06u4fnlf	1.jpeg	http://res.cloudinary.com/dgn07k2h0/image/upload/v1681890902/event-be/tol7vf1drrir06u4fnlf.jpg	\N	\N
54	2023-04-19 14:56:47.952	GCC19100	event-be/z7qqt6tm82cthuixm5hx	340978013_603861354979665_3030001766629577431_n.jpg	http://res.cloudinary.com/dgn07k2h0/image/upload/v1681891007/event-be/z7qqt6tm82cthuixm5hx.jpg	\N	52
102	2023-04-19 18:22:26.895	GCC19100	event-be/k5leiwefgvcrupnctrnv	337416200_908636237143426_2403204448597958396_n.jpg	http://res.cloudinary.com/dgn07k2h0/image/upload/v1681903346/event-be/k5leiwefgvcrupnctrnv.jpg	\N	102
153	2023-04-19 23:37:26.051	GCC19100	event-be/n1nk3j8errzkrqko5p7e	1.jpeg	http://res.cloudinary.com/dgn07k2h0/image/upload/v1681922245/event-be/n1nk3j8errzkrqko5p7e.jpg	\N	\N
\.


--
-- TOC entry 3666 (class 0 OID 32791)
-- Dependencies: 217
-- Data for Name: major; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.major (major_id, created_at, created_by, description, name) FROM stdin;
3	2023-04-19 17:09:57.982	GCC19100	\N	SE - Information Technology
4	2023-04-19 17:10:38.394	GCC19100	\N	BA - Event
5	2023-04-19 17:12:28.734	GCC19100	\N	BA - Business
6	2023-04-19 17:12:39.171	GCC19100	\N	GD - Graphics Design
2	2023-04-19 17:09:44.49	GCC19100	\N	BA - Maketing 1
\.


--
-- TOC entry 3667 (class 0 OID 32798)
-- Dependencies: 218
-- Data for Name: messages; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.messages (id, created_at, created_by, description, type, users_id) FROM stdin;
5e15dbc4-d3ad-446d-b641-cf2aab8d2363	2023-04-16 13:45:30.621	GCC19101	List of ongoing events	SEND	GCC19101
262faabc-fc87-48f8-9233-9fe8f85ee4ce	2023-04-16 13:45:30.723	DEFAULT_MESSAGE	There are no events going on	RECEIVE	GCC19101
117d4d96-5372-422f-a858-4ae7f1045718	2023-04-16 13:45:42.008	GCC19101	List of ongoing events	SEND	GCC19101
d87e6cee-a905-4137-ad35-ce3cd77f7bda	2023-04-16 13:45:42.011	DEFAULT_MESSAGE	There are no events going on	RECEIVE	GCC19101
d8387fb8-7769-4044-a840-fcec51ea004a	2023-04-16 13:49:23.66	GCC19101	List of ongoing events	SEND	GCC19101
0e8b1bc8-5d47-4ed0-b171-80b7e854ab50	2023-04-16 13:49:23.668	DEFAULT_MESSAGE	1.Event: Hi #2	RECEIVE	GCC19101
5751211b-525b-4d9c-8a61-1b528a3858f4	2023-04-16 13:49:33.854	GCC19101	1.Event: Hi #2	SEND	GCC19101
b731583a-292a-4509-809a-7bbe3b863902	2023-04-16 13:49:33.863	DEFAULT_MESSAGE	1. Event time Hi #2	RECEIVE	GCC19101
8a0d3c81-9b85-4153-9494-786734820eed	2023-04-16 13:49:40.769	GCC19101	1. Event time Hi #2	SEND	GCC19101
3ae97d93-d9c8-4735-b679-9aabed565f99	2023-04-16 13:49:40.771	DEFAULT_MESSAGE	Event time: 2023-04-15 20:18:00.0 -> 2023-04-18 20:18:00.0	RECEIVE	GCC19101
3b3e9f16-ba67-4889-909e-b3289760acae	2023-04-16 13:49:42.978	GCC19101	2. The event takes place: Hi #2	SEND	GCC19101
a969741a-3b81-41da-8a5e-917cdeebb001	2023-04-16 13:49:42.979	DEFAULT_MESSAGE	The event takes place: 3A	RECEIVE	GCC19101
d4312fa3-17b3-4563-91f7-cec87259e7ea	2023-04-19 18:40:54.889	GCC19101	List of ongoing events	SEND	GCC19101
98108730-301e-4f51-a7c3-cdd902d4d400	2023-04-19 18:40:54.95	DEFAULT_MESSAGE	1.Event: MEET GRAPHICS PROFESSOR AT GREENWICH UK #102	RECEIVE	GCC19101
0cb55360-8fb6-4f12-b567-b22bfda70b2e	2023-04-19 18:40:54.955	DEFAULT_MESSAGE	2.Event: Hi #2	RECEIVE	GCC19101
8ef787d0-0554-4868-acbf-948396abc130	2023-04-19 19:10:54.671	GCC19101	List of ongoing events	SEND	GCC19101
ed9cd9d3-7168-4343-9093-c13a1c27eea2	2023-04-19 19:10:54.735	DEFAULT_MESSAGE	1.Event: MEET GRAPHICS PROFESSOR AT GREENWICH UK #102	RECEIVE	GCC19101
53fe289d-ccd5-4ee0-8561-4564a4d01d8a	2023-04-19 19:10:54.736	DEFAULT_MESSAGE	2.Event: Hi #2	RECEIVE	GCC19101
1022484e-f535-4489-88bb-e68a398400d2	2023-04-19 19:10:58.366	GCC19101	1.Event: MEET GRAPHICS PROFESSOR AT GREENWICH UK #102	SEND	GCC19101
59e29422-8587-4ecc-9110-3b43cfadb9e0	2023-04-19 19:10:58.374	DEFAULT_MESSAGE	1. Event time MEET GRAPHICS PROFESSOR AT GREENWICH UK #102	RECEIVE	GCC19101
aae0a300-85d7-4474-9eaa-84c287e2a309	2023-04-19 19:11:03.819	GCC19101	1. Event time MEET GRAPHICS PROFESSOR AT GREENWICH UK #102	SEND	GCC19101
461c0eb8-afe6-46ba-a728-fcb40278437c	2023-04-19 19:11:03.834	DEFAULT_MESSAGE	Event time: 2023-04-18 00:22:00.0 -> 2023-04-21 02:22:00.0	RECEIVE	GCC19101
06b02d0c-81b7-4856-8762-b548bdcb2286	2023-04-19 19:11:09.637	GCC19101	2. The event takes place: MEET GRAPHICS PROFESSOR AT GREENWICH UK #102	SEND	GCC19101
ace5aad9-41b7-4355-9905-96c096418e91	2023-04-19 19:11:09.643	DEFAULT_MESSAGE	The event takes place: Hall 3A, Greenwich Vietnam - Cantho campus	RECEIVE	GCC19101
e8ad91b9-3a40-4bcb-99d9-e3b628f587a7	2023-04-19 19:11:14.483	GCC19101	hi	SEND	GCC19101
fce00d0c-91ef-4dcb-92f1-7d361c0e4c25	2023-04-19 19:11:14.483	GCC19101	hi	SEND	GCC19101
1cdf20bd-6428-4efd-98d7-a232d3ecf93c	2023-04-19 19:11:23.947	GCC19101	hi	SEND	GCC19101
a4659bff-317a-45bb-bf12-064a1027b80c	2023-04-19 19:11:23.947	GCC19101	hi	SEND	GCC19101
c75e4c2e-678c-459e-b5df-09a8dae3b5c2	2023-04-19 19:11:30.891	GCC19101	22	SEND	GCC19101
452529bc-2af2-425d-9468-4dd0a51186f5	2023-04-19 19:11:33.842	GCC19101	22	SEND	GCC19101
034ce846-3bd0-411c-9feb-2f1a8e7fbd2e	2023-04-19 19:11:44.698	GCC19101	3	SEND	GCC19101
daff1201-c120-4959-94a6-f5aa67be0b58	2023-04-19 19:14:28.928	GCC19100	4	RECEIVE	GCC19101
bdf51109-9b79-4527-bb80-6fb454b82b51	2023-04-19 22:27:05.592	GCC19101	hi	SEND	GCC19101
c7ad030f-f255-4b94-b25e-1b88a0274281	2023-04-19 22:27:22.198	GCC19101	3	SEND	GCC19101
861478a0-d995-40cf-bd36-d85528601d90	2023-04-19 22:27:57.667	GCC19101	5	SEND	GCC19101
70700975-504d-4f08-b0e7-2cec0c85773e	2023-04-19 22:28:02.247	GCC19101	7	SEND	GCC19101
afefb090-405b-4f1c-a272-593397eff9da	2023-04-19 22:28:04.416	GCC19101	8	SEND	GCC19101
c845ae76-0093-4e85-b4db-824fdba3f96c	2023-04-19 22:28:06.049	GCC19101	9	SEND	GCC19101
5a693c0f-347d-491e-a306-dbb4494f4bd1	2023-04-19 22:28:19.479	GCC19101	List of ongoing events	SEND	GCC19101
97f9998c-e3a8-47f6-8254-86b7cd451b0e	2023-04-19 22:28:19.485	DEFAULT_MESSAGE	1.Event: MEET GRAPHICS PROFESSOR AT GREENWICH UK #102	RECEIVE	GCC19101
61f985b7-f48a-48bb-b8d1-9cd0e41b3b83	2023-04-19 22:28:19.487	DEFAULT_MESSAGE	2.Event: Hi #2	RECEIVE	GCC19101
7735b1eb-1b45-4986-9f96-47210e581060	2023-04-19 22:28:26.527	GCC19101	Exit	SEND	GCC19101
\.


--
-- TOC entry 3668 (class 0 OID 32805)
-- Dependencies: 219
-- Data for Name: notification; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.notification (notification_id, created_at, description, event_id, is_read, users_users_id) FROM stdin;
1	2023-04-16 13:47:07.522	Register event Hi successfully	2	t	GCC19101
\.


--
-- TOC entry 3669 (class 0 OID 32812)
-- Dependencies: 220
-- Data for Name: recommend; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.recommend (recommend_id, created_at, created_by, description, name, users_users_id) FROM stdin;
\.


--
-- TOC entry 3670 (class 0 OID 32819)
-- Dependencies: 221
-- Data for Name: sequence_value_item; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.sequence_value_item (seq_name, created_at, last_updated_stamp, last_updated_tx_stamp, seq_id) FROM stdin;
vn.kien.event.eventbe.entity.Users	\N	2023-04-19 23:30:50.029	\N	105
\.


--
-- TOC entry 3671 (class 0 OID 32824)
-- Dependencies: 222
-- Data for Name: ticket; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ticket (object_id, created_at, created_by, description, name, object_type, event_id) FROM stdin;
37eddb6f-b485-4abd-8611-9f7231b60fc1	2023-04-16 13:46:09.231	GCC19101	hi	GCC19101	QUESTION	2
\.


--
-- TOC entry 3672 (class 0 OID 32831)
-- Dependencies: 223
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (users_id, birthday, created_at, created_by, email, full_name, gender, password, phone, roles, course_id, major_id) FROM stdin;
GCC19100	2023-04-16 12:43:11.662	2023-04-16 12:43:11.886	\N	kienle0660@gmail.com	Le Trung Kien Admin	MALE	$2a$10$hTg7ALD1tQP6dP.ZYlOhue7MqC2njhk/iwcwnWhlh6a1XKcCqOk32	0123456789	ROLE_ADMIN	\N	\N
GCC19101	2023-04-17 07:00:00	2023-04-16 12:54:36.557	GCC19100	kienltgcc19138@fpt.edu.vn	Le Trung Kien	MALE	$2a$10$4FjLJQxz1ltxj19DoPWDMOwijr2Ammb7ABaMOF4xvvmi1VjNOG1/i	0989765877	ROLE_USER	1	3
GCC19102	2001-07-22 07:00:00	2023-04-19 17:14:52.144	GCC19100	phatttgcc19181@fpt.edu.vn	Tran Tan Phat	MALE	$2a$10$nuLKweCiEhYfn5xp9z4UO.3DNJSE8g59Z8cDNQqYdOcfcOhn91rtS	0706667675	ROLE_USER	2	3
\.


--
-- TOC entry 3673 (class 0 OID 32838)
-- Dependencies: 224
-- Data for Name: users_event; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users_event (event_id, users_id, created_at, is_join, score) FROM stdin;
2	GCC19101	2023-04-16 13:46:09.244	t	12
\.


--
-- TOC entry 3674 (class 0 OID 32843)
-- Dependencies: 225
-- Data for Name: users_preference; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users_preference (users_preference_id, code, created_at, type, users_id) FROM stdin;
50ec09db-d0b9-4c57-8a2b-e68304c6d895	359127	2023-04-16 13:21:25.292	VERIFY_LOGIN	GCC19101
336a0500-1a50-40d6-a012-602175adcf1e	637373	2023-04-19 18:23:44.715	VERIFY_LOGIN	GCC19101
95a8f209-7a16-4ad2-8dee-3bfcf617a0eb	354661	2023-04-21 01:40:01.424	VERIFY_LOGIN	GCC19101
3e12eb39-42e3-493a-a728-97a7ffda3a0e	212975	2023-04-21 01:40:15.792	VERIFY_LOGIN	GCC19101
a2244965-3654-4eae-ba2d-882c429da1da	812793	2023-04-21 01:40:30.465	VERIFY_LOGIN	GCC19101
94785ae8-c17f-4614-ad85-aecf4bb1f667	591606	2023-04-21 01:40:53.318	VERIFY_LOGIN	GCC19101
9c0a9fcb-a29f-4f9c-9153-21c99fe2796b	584320	2023-04-21 01:41:43.642	VERIFY_LOGIN	GCC19101
8ea48578-3932-4e31-89d8-e123d1a1b863	922665	2023-04-21 01:43:22.219	VERIFY_LOGIN	GCC19101
e6258416-2213-462e-b1f2-a526caac228f	467757	2023-04-21 01:43:28.48	VERIFY_LOGIN	GCC19101
a39f38fc-1ea3-42da-bd09-0b11edcbfcc6	856715	2023-04-21 01:43:48.104	VERIFY_LOGIN	GCC19101
155995a7-623b-4d1e-aa21-2d2cbe047133	942176	2023-04-21 01:44:14.536	VERIFY_LOGIN	GCC19101
aaad20d7-869a-47b3-9b6d-cee9d18b7491	957387	2023-04-23 21:35:17.391	VERIFY_LOGIN	GCC19101
\.


--
-- TOC entry 3686 (class 0 OID 0)
-- Dependencies: 226
-- Name: course_generator; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.course_generator', 51, true);


--
-- TOC entry 3687 (class 0 OID 0)
-- Dependencies: 227
-- Name: event_generator; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.event_generator', 201, true);


--
-- TOC entry 3688 (class 0 OID 0)
-- Dependencies: 228
-- Name: file_data_generator; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.file_data_generator', 201, true);


--
-- TOC entry 3689 (class 0 OID 0)
-- Dependencies: 229
-- Name: major_generator; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.major_generator', 51, true);


--
-- TOC entry 3690 (class 0 OID 0)
-- Dependencies: 230
-- Name: notification_generator; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.notification_generator', 1, true);


--
-- TOC entry 3691 (class 0 OID 0)
-- Dependencies: 231
-- Name: recommend_generator; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.recommend_generator', 1, false);


--
-- TOC entry 3489 (class 2606 OID 32776)
-- Name: course course_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.course
    ADD CONSTRAINT course_pkey PRIMARY KEY (course_id);


--
-- TOC entry 3491 (class 2606 OID 32783)
-- Name: event event_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.event
    ADD CONSTRAINT event_pkey PRIMARY KEY (event_id);


--
-- TOC entry 3493 (class 2606 OID 32790)
-- Name: file file_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.file
    ADD CONSTRAINT file_pkey PRIMARY KEY (file_data_id);


--
-- TOC entry 3495 (class 2606 OID 32797)
-- Name: major major_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.major
    ADD CONSTRAINT major_pkey PRIMARY KEY (major_id);


--
-- TOC entry 3497 (class 2606 OID 32804)
-- Name: messages messages_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT messages_pkey PRIMARY KEY (id);


--
-- TOC entry 3499 (class 2606 OID 32811)
-- Name: notification notification_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notification
    ADD CONSTRAINT notification_pkey PRIMARY KEY (notification_id);


--
-- TOC entry 3501 (class 2606 OID 32818)
-- Name: recommend recommend_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recommend
    ADD CONSTRAINT recommend_pkey PRIMARY KEY (recommend_id);


--
-- TOC entry 3503 (class 2606 OID 32823)
-- Name: sequence_value_item sequence_value_item_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sequence_value_item
    ADD CONSTRAINT sequence_value_item_pkey PRIMARY KEY (seq_name);


--
-- TOC entry 3505 (class 2606 OID 32830)
-- Name: ticket ticket_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ticket
    ADD CONSTRAINT ticket_pkey PRIMARY KEY (object_id);


--
-- TOC entry 3509 (class 2606 OID 32842)
-- Name: users_event users_event_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_event
    ADD CONSTRAINT users_event_pkey PRIMARY KEY (event_id, users_id);


--
-- TOC entry 3507 (class 2606 OID 32837)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (users_id);


--
-- TOC entry 3511 (class 2606 OID 32849)
-- Name: users_preference users_preference_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_preference
    ADD CONSTRAINT users_preference_pkey PRIMARY KEY (users_preference_id);


--
-- TOC entry 3512 (class 2606 OID 32856)
-- Name: file fk8wmrqwmt68do2u3oq1ps5ewui; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.file
    ADD CONSTRAINT fk8wmrqwmt68do2u3oq1ps5ewui FOREIGN KEY (event_id) REFERENCES public.event(event_id);


--
-- TOC entry 3515 (class 2606 OID 32871)
-- Name: recommend fkbgeat6596eq6gsqs8bhyjxe7g; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.recommend
    ADD CONSTRAINT fkbgeat6596eq6gsqs8bhyjxe7g FOREIGN KEY (users_users_id) REFERENCES public.users(users_id);


--
-- TOC entry 3516 (class 2606 OID 32876)
-- Name: ticket fkfytuhjopeamxbt1cpudy92x5n; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ticket
    ADD CONSTRAINT fkfytuhjopeamxbt1cpudy92x5n FOREIGN KEY (event_id) REFERENCES public.event(event_id);


--
-- TOC entry 3519 (class 2606 OID 32891)
-- Name: users_event fkhter2rnwci4f6blnylcm665ak; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_event
    ADD CONSTRAINT fkhter2rnwci4f6blnylcm665ak FOREIGN KEY (event_id) REFERENCES public.event(event_id);


--
-- TOC entry 3514 (class 2606 OID 32866)
-- Name: notification fkivato1siglgf4edh9qdq68i9a; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notification
    ADD CONSTRAINT fkivato1siglgf4edh9qdq68i9a FOREIGN KEY (users_users_id) REFERENCES public.users(users_id);


--
-- TOC entry 3517 (class 2606 OID 32881)
-- Name: users fkjgrmlnkr4e2fydjxf1he5ykgo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fkjgrmlnkr4e2fydjxf1he5ykgo FOREIGN KEY (course_id) REFERENCES public.course(course_id);


--
-- TOC entry 3513 (class 2606 OID 32861)
-- Name: messages fkk208s2vdr0teqfb319mbi8f2u; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.messages
    ADD CONSTRAINT fkk208s2vdr0teqfb319mbi8f2u FOREIGN KEY (users_id) REFERENCES public.users(users_id);


--
-- TOC entry 3518 (class 2606 OID 32886)
-- Name: users fkr9xy1qys645mebakvhfkls5l3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fkr9xy1qys645mebakvhfkls5l3 FOREIGN KEY (major_id) REFERENCES public.major(major_id);


--
-- TOC entry 3520 (class 2606 OID 32896)
-- Name: users_event fktp78fn8eqg2p1kn0sc4yr9fk3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users_event
    ADD CONSTRAINT fktp78fn8eqg2p1kn0sc4yr9fk3 FOREIGN KEY (users_id) REFERENCES public.users(users_id);


-- Completed on 2023-04-23 23:29:29 +07

--
-- PostgreSQL database dump complete
--

