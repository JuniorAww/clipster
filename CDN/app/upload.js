const multer = require("multer");
const path = require("node:path");

const storageConfig = multer.diskStorage({
	destination: path.join(__dirname, "tmp"),
	filename: (req, file, res) => {
		res(null, Date.now() + "-" + file.originalname);
	},
});

const fileFilterConfig = function(req, file, cb) {
	if (file.mimetype === "video/mp4"
		//file.mimetype === "image/jpeg"
		//|| file.mimetype === "image/png"
		) {
		cb(null, true);
	} else {
		cb(null, false);
	}
};

const upload = multer({
	storage: storageConfig,
	limits: {
		fileSize: 1024 * 1024 * 20
	},
	fileFilter: fileFilterConfig,
});

module.exports = upload;
