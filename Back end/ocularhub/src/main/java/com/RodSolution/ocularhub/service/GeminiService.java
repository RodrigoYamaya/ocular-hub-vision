package com.RodSolution.ocularhub.service;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.content.Media;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class GeminiService {

    //Vamos usar final para evitar que nao seja alterado em tempo execução,Assim garantido que objeto nao seja quando instanciado
    private final ChatModel chatModel;

    // Injeção via construtor (Boa prática recomendada pelo Spring).Melhor que autowired
    public GeminiService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String gerarAnaliseComImagem(String tituloExame, String regiaoAnalisada, MultipartFile imagem) {
        try {
            // 1. Aqui O prompt que instrui a IA
            String instrucao = String.format(
                    "Atue como um especialista em oftalmologia. Analise a imagem anexa referente ao exame: " +
                            "Título: %s. Região Analisada: %s. " +
                            "Com base exclusivamente no que você consegue visualizar na imagem, gere um diagnóstico estruturado. " +
                            "Seja conciso e indique se há necessidade de retorno médico.",
                    tituloExame, regiaoAnalisada
            );

            // 2. Aqui vai Prepara o arquivo da imagem recebida do front-end
            ByteArrayResource resource = new ByteArrayResource(imagem.getBytes());

            // Aqui vai Garantir um fallback caso o ContentType venha nulo do request
            String contentType = imagem.getContentType() != null ? imagem.getContentType() : "image/jpeg";
            Media media = new Media(MimeTypeUtils.parseMimeType(contentType), resource);

            // 3. Aqui vai Juntar o texto com a imagem na mesma mensagem
            UserMessage userMessage = UserMessage.builder()
                    .text(instrucao)
                    .media(media)
                    .build();
            Prompt prompt = new Prompt(userMessage);

            // 4. Manda pro GEMINI e pega o texto de volta!
            ChatResponse response = chatModel.call(prompt);

            if (response != null && response.getResult() != null && response.getResult().getOutput() != null) {
                return response.getResult().getOutput().getText();
            }

            return "A IA não retornou nenhuma análise para este exame.";

        } catch (Exception e) {
            return "Erro na comunicação com a inteligência artificial visual: " + e.getMessage();
        }
    }
}
