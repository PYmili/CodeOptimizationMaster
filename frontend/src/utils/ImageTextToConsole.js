import {Jimp} from "jimp";
import ImageData from "@canvas/image-data";
import {imageToText} from "char-dust";
import logger from "@/utils/logger.js";

export const printImageToConsole = (imagePath) => {
    Jimp.read(imagePath).then((image) => {
        // Magnify x
        // image.scale(1);
        const imageData = new ImageData(
            Uint8ClampedArray.from(image.bitmap.data),
            image.bitmap.width,
            image.bitmap.height
        );

        let result = "";
        imageToText(imageData).forEach(line => result += line + "\n");
        logger.info(result);
    });
}