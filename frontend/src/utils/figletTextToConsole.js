import figlet from "figlet";
import logger from "@/utils/logger.js";

export const printConsole = async (text) => {
    try {
        const response = await fetch('/fonts/Standard.flf')
        const fontData = await response.text()

        figlet.parseFont('Standard', fontData)

        await figlet.text(text, 'Standard', (err, data) => {
            if (err) {
                logger.error('Figlet error:', err)
                return
            }
            logger.info(data);
        })
    } catch (err) {
        logger.error('Failed to load font:', err)
    }
}