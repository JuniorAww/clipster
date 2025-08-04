const express = require("express");
const app = express();

const upload = require('./upload')

const PORT = 8085;

/*app.get("/", (req, res) => {
	res.send("Hello from Express!");
});*/

app.post("/upload", upload.single("file"), (req, res) => {
	if (!req.file) {
		res.status(413).send(`File not uploaded!`);
		return;
	}
	res.status(201).send("Files uploaded successfully");
});

app.listen(PORT, () => {
console.log(`Node server started :${PORT}`);
});
