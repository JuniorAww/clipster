FROM node:23.7.0-alpine3.20 AS installer
WORKDIR /app

COPY package*.json .

RUN npm install

FROM node:23.7.0-alpine3.20 AS release
WORKDIR /app

COPY --from=installer /app /app
COPY . .

CMD ["npm","start"]
