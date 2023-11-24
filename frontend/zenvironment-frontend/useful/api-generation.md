# API generator
The api is generated with [swagger-typescript-api](https://www.npmjs.com/package/swagger-typescript-api)

In the root directory run: `node scripts/generate-api-dev.js`

or in this readme:
```shell
node ../scripts/generate-api.js 
```

Configuration located in `scripts/generate-api.js`.

The generated file is located in `src/lib/api/generated`.