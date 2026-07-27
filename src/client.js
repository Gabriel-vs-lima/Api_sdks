import { GoogleGenAI } from "@google/genai";
import 'dotenv/config'
const ai = new GoogleGenAI({});

const interaction = await ai.interactions.create({
  model: "gemini-3.5-flash",
  input: "Quem é você?",
});
console.log(interaction.output_text);