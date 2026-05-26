# TestMate AI Deployment Guide

This repo is set up for:

- Render: Spring Boot backend and test execution API
- Vercel: Vite React dashboard
- Supabase: Postgres persistence for execution history and logs

## Render Backend

Deploy the backend from the repository root using `render.yaml`.

Required environment variables:

```text
TESTMATE_API_KEY=<shared dashboard/backend API key>
TESTMATE_ALLOWED_ORIGINS=https://your-vercel-app.vercel.app,http://localhost:5173
SPRING_DATASOURCE_URL=jdbc:postgresql://<host>:5432/postgres?sslmode=require
SPRING_DATASOURCE_USERNAME=<supabase database user>
SPRING_DATASOURCE_PASSWORD=<supabase database password>
```

Recommended execution limits for the free plan:

```text
TESTMATE_EXECUTION_CORE_POOL_SIZE=1
TESTMATE_EXECUTION_MAX_POOL_SIZE=2
TESTMATE_EXECUTION_QUEUE_CAPACITY=5
SPRING_DATASOURCE_MAX_POOL_SIZE=5
SPRING_DATASOURCE_MIN_IDLE=1
```

Remote Playwright browser execution:

```text
WEB_EXECUTION_MODE=remote
WEB_BROWSER=chromium
WEB_REMOTE_WS_ENDPOINT=wss://your-playwright-browser-service/ws
WEB_REMOTE_CONNECT_TIMEOUT_MS=30000
WEB_REMOTE_HEADERS=Authorization=Bearer <token>
```

For Chromium CDP endpoints, use:

```text
WEB_EXECUTION_MODE=cdp
WEB_BROWSER=chromium
WEB_REMOTE_CDP_ENDPOINT=http://your-browser-service:9222
```

Keep `WEB_EXECUTION_MODE=local` when Render should launch browsers in the backend container itself. Use `remote` or `cdp` when browser capacity is provided by a dedicated Playwright browser service/grid.

Health check:

```text
GET /api/framework/health
```

## Vercel Frontend

Deploy from the repository root. The root `vercel.json` builds the Vite dashboard from `frontend/` and publishes `frontend/dist`.

Required environment variables:

```text
VITE_API_BASE_URL=https://your-render-backend.onrender.com
VITE_TESTMATE_API_KEY=<same value as TESTMATE_API_KEY>
```

Build settings:

```text
Build Command: cd frontend && npm run build
Output Directory: frontend/dist
Install Command: cd frontend && npm ci
Node.js: 20.19.0 or newer
```

## Supabase

Use Supabase Postgres as the Spring datasource. Prefer a pooled connection string for deployed services, keep the Hikari pool small on free-tier infrastructure, and keep credentials in Render environment variables only.

The application currently uses Hibernate `ddl-auto=update` for early-stage deployment convenience. Before production use, replace this with versioned migrations through Flyway or Liquibase.

## Security Notes

- `TESTMATE_API_KEY` enables a lightweight API key gate for execution and report endpoints.
- Keep `TESTMATE_ALLOWED_ORIGINS` restricted to known local and deployed frontend URLs.
- The execution API runs Maven test processes, so do not deploy it publicly without the API key and concurrency limits.
