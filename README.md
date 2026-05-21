# Lachelein - Backend

> 세미를 위한 세미 해커톤 | AI 기반 애니메이션 추천 서비스

---

## 시연 영상

<!-- 영상을 추가하려면 아래 방법 중 하나를 사용하세요 (아래 '시연 영상 추가 방법' 섹션 참고) -->

---

## 기술 스택

| 분류 | 기술 |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.5.4 |
| ORM | Spring Data JPA |
| Database | H2 (배포) / MySQL, MariaDB (로컬) |
| AI | Spring AI + Gemini 2.5 Flash |
| Auth | Kakao OAuth 2.0 |
| API Docs | SpringDoc OpenAPI (Swagger) |
| Build | Gradle |

---

## 주요 기능

### 카카오 소셜 로그인
- Authorization Code 방식으로 카카오 OAuth 2.0 직접 구현
- 인가 코드 → access token 발급 → 사용자 정보 조회 → 회원 저장/조회

### AI 애니메이션 추천
- 사용자 취향 5개 항목(분위기, 장르, 이야기 중심, 전개 속도, 주제) 기반 프롬프트 생성
- Gemini 2.5 Flash가 애니메이션 5개 추천 → TMDB ID 변환 후 DB 저장
- AI 호출 실패 시 폴백 목록 자동 반환

### 찜하기
- 회원별 콘텐츠(애니/영화) 찜 추가 / 취소 / 목록 조회
- 중복 찜 방지 예외처리

---

## API 목록

### Auth
| Method | URL | 설명 |
|---|---|---|
| POST | `/api/auth/kakao/login` | 카카오 로그인 |
| POST | `/api/auth/kakao/logout` | 카카오 로그아웃 |

### Anime
| Method | URL | 설명 |
|---|---|---|
| POST | `/api/anime/recommend/{memberId}` | AI 애니 추천 |
| GET | `/api/anime/lists` | 전체 추천 목록 조회 |

### Favorites
| Method | URL | 설명 |
|---|---|---|
| POST | `/api/favorites/{memberId}` | 찜 추가 |
| DELETE | `/api/favorites/{memberId}/{tmdbId}` | 찜 취소 |
| GET | `/api/favorites/{memberId}` | 찜 목록 조회 |

> Swagger UI: `http://localhost:8080/swagger-ui/index.html`

---

## 로컬 실행 방법

**1. 환경변수 설정**

`.env` 파일을 `BE_repo/` 루트에 생성합니다.

```env
KAKAO_KEY=카카오_REST_API_KEY
KAKAO_REDIRECT_URL=http://localhost:8080/api/auth/kakao/callback

GEMINI_API_KEY=GEMINI_API_KEY
GEMINI_BASE_URL=https://generativelanguage.googleapis.com/v1beta/openai/
COMPLETION_URL=/chat/completions

TMDB_API_KEY=TMDB_Bearer_Token
TMDB_BASE_URL=https://api.themoviedb.org/3
```

**2. 실행**

```bash
./gradlew bootRun
```

---

## 패키지 구조

```
src/main/java/com/semiton/Lachelein
├── animeList       # AI 추천 / TMDB 연동
├── favorite        # 찜하기
├── member          # 회원 / 카카오 로그인
├── config          # CORS 설정
└── appConfig       # Bean 설정
```

---

## 팀원

| 이름 | 역할 |
|---|---|
| 박상원 | Backend |

---

## 시연 영상 추가 방법

**방법 1 — YouTube 업로드 후 썸네일 링크**
```markdown
[![시연 영상](https://img.youtube.com/vi/유튜브_VIDEO_ID/maxresdefault.jpg)](https://www.youtube.com/watch?v=유튜브_VIDEO_ID)
```

**방법 2 — GitHub에 직접 영상 업로드**
1. 이 저장소의 아무 이슈나 PR에서 댓글 창에 `.mp4` 파일을 드래그 앤 드롭
2. 업로드되면 `https://github.com/user-attachments/assets/...` 형태의 URL이 생성됨
3. 해당 URL을 README에 붙여넣으면 GitHub가 자동으로 영상으로 렌더링

```markdown
https://github.com/user-attachments/assets/여기에_업로드된_URL.mp4
```
