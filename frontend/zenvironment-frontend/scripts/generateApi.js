const { generateApi } = require('swagger-typescript-api');
const path = require('path');
const fs = require('fs');

generateApi({
  name: 'apiTypes',
  url: 'http://localhost:8080/v3/api-docs',
  httpClientType: 'axios',
  extractEnums: true,
  generateUnionEnums: true,
})
  .then(({ files }) => {
    const { fileContent } = files[0];
    const outPath = path.join(__dirname, '..', 'src/lib/api/generated/generated-api.ts');
    fs.writeFileSync(outPath, fileContent);
  })
  .catch((e) => {
    console.error(e);
  });
