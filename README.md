# ChatBot

## 📌 프로젝트 개요
이 프로젝트는 OpenAI API를 활용하여 **AI 챗봇 기능**을 제공하는 웹 애플리케이션입니다.  
회원가입 및 로그인 기능을 제공하며, 사용자는 AI 챗봇과 대화를 나눌 수 있습니다.  

---

## ⚡ 주요 기능
### 1️⃣ 사용자 관리 및 인증
- **회원가입**: 이메일, 패스워드, 이름을 입력하여 계정 생성  
- **로그인**: 이메일과 패스워드로 로그인 시 **JWT 토큰 발급**  
- **인증**: 모든 API 요청은 JWT를 이용하여 인증  
- **권한 부여**: `ROLE_USER`, `ROLE_ADMIN` 권한 분리  

### 2️⃣ 대화 관리 (챗봇 기능)
- **챗봇과 대화**: OpenAI API를 호출하여 AI가 질문에 대한 답변을 생성  
- **대화 내역 저장**: 사용자의 대화는 **스레드(Thread)** 단위로 저장  
- **대화 조회**: 사용자는 자신의 대화 내역을 확인 가능  

---

## 🛠 기술 스택
- **Spring Boot 3.x.x**  
- **Java 17+**  
- **PostgreSQL 15.8**  
- **Spring Security**  
- **Spring Data JPA**  
- **SpringDoc OpenAPI**  

---

## 🚀 실행 방법
### 1️⃣ 프로젝트 클론
```sh
git clone https://github.com/ghtjr410/ChatBot.git
cd chatbot


2️⃣ 환경 변수 설정
.env 또는 application.properties에 OpenAI API 키를 설정해야 합니다.
(GitHub 저장소에 API 키를 노출하지 마세요!)

properties
코드 복사
openai.api.url=https://api.openai.com/v1/chat/completions
openai.api.key=your-api-key
openai.api.model=gpt-4o-mini
openai.api.media-type=application/json; charset=UTF-8
3️⃣ 애플리케이션 실행
(1) PostgreSQL을 사용할 경우
sh
코드 복사
docker-compose up -d  # PostgreSQL 실행
./gradlew bootRun

🔑 API 문서 (Swagger)
로컬 서버 실행 후 Swagger UI 접속
📌 http://localhost:4040/swagger-ui/index.html
