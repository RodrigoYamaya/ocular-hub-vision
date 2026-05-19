import os
import cv2
import pandas as pd

# Caminhos do nosso Data Lake (Lendo do HD, Código no SSD) que nos pensamos em por no hd visto que faria mais sentido na nuvem S3 AWS
PATH_BRONZE = "D:/ocularhub_data_lake/bronze"
PATH_SILVER = "D:/ocularhub_data_lake/silver"
CSV_PATH = "trainLabels.csv"

def mapear_imagens_bronze():
    """Varre todas as pastas da bronze e cria um dicionário para achar as fotos na hora"""
    print("Mapeando imagens na camada Bronze (Aguarde alguns segundos)...")
    mapa = {}
    
    # os.walk entra em TODAS as subpastas procurando imagens(os walk e uma função do python que percorrer automaticamente pastas)
    for raiz, _, arquivos in os.walk(PATH_BRONZE):
        for arquivo in arquivos:
            if arquivo.lower().endswith(('.jpeg', '.jpg', '.png')):
                # Aki vamos Remover a extensão para cruzar com o nome que está no CSV
                nome_sem_extensao = os.path.splitext(arquivo)[0]
                mapa[nome_sem_extensao] = os.path.join(raiz, arquivo)
                
    print(f"Mapeamento concluído! {len(mapa)} imagens encontradas no seu HD.")
    return mapa

def processar_etl():
    # 1.Aki vai  Verifica se o CSV está no lugar no caminho certo
    if not os.path.exists(CSV_PATH):
        print(f"ERRO: O arquivo '{CSV_PATH}' não foi encontrado na pasta do código.")
        return
    # aki ele vai ler o CSV    
    df = pd.read_csv(CSV_PATH)
    
    # 2. Aki vai Cria automaticamente as pastas 0 a 4 dentro da Silver
    for i in range(5):
        os.makedirs(os.path.join(PATH_SILVER, str(i)), exist_ok=True)
        
    # 3.Aki vai  Chamar o nosso 'Radar' de imagens na camada bronze
    mapa_imagens = mapear_imagens_bronze()
    
    # 4. Configuração do Filtro Médico (CLAHE) - Para saltar as veias do olho(usado para destacar as veias dos olhos assim gerando mais precisão na IA)
    clahe = cv2.createCLAHE(clipLimit=2.0, tileGridSize=(8,8))
    contador = 0
    
    print("\nIniciando a limpeza e aplicação dos filtros nas retinas...")
    
    # 5. aki e o  Motor Principal do ETL (Lêr o CSV linha por linha)
    for index, row in df.iterrows():
        nome_imagem = str(row['image'])
        classe = str(row['level']) # 0, 1, 2, 3 ou 4
        
        # Verifica se a imagem pedida no CSV realmente está no nosso HD(Se as imagem no data lake que estamos usando nosso proprio hd)
        if nome_imagem in mapa_imagens:
            caminho_origem = mapa_imagens[nome_imagem]
            caminho_destino = os.path.join(PATH_SILVER, classe, f"{nome_imagem}.jpeg")
            
            # SUPERPODER: Se a foto já existe na Silver, ele pula para não refazer trabalho.Observações que podem haver iamgens nao tratadas.Então por esse motivo vamos tratat todas.
            if os.path.exists(caminho_destino):
                continue
                
            try:
                # --- Aki E O CORAÇÃO DO OPENCV ---
                # A. Abre a imagem bruta
                img = cv2.imread(caminho_origem)
                
                # B. Redimensiona para não travar minha RAM e padronizar PARA IA(Ultron)
                img_resized = cv2.resize(img, (224, 224))
                
                # C. Transforma em tons de cinza (obrigatório para cinza,Visto que maioria dos exames sao em cinza para realçar as possiveis danos vasos sanguineos ou hemorragias)
                img_gray = cv2.cvtColor(img_resized, cv2.COLOR_BGR2GRAY)
                
                # D. Aplica o filtro matemático nas veias(Para destacar e aumentar precisao da nossa IA(Ultron)
                img_final = clahe.apply(img_gray)
                
                # E. Salva a foto pronta na pasta da respectiva doença
                cv2.imwrite(caminho_destino, img_final)
                
                contador += 1
                
                # Avisa no terminal a cada 500 fotos prontas(PARA MANTER tudo organizado)
                if contador % 500 == 0:
                    print(f"{contador} imagens já foram limpas e salvas na Silver!")
                    
            except Exception as e:
                print(f"Aviso: Erro ao processar a imagem {nome_imagem}: {e}")
                
    print(f"\nETL FINALIZADO! {contador} novas imagens processadas.")
    print("A camada Silver está limpa e organizada em pastas. Tudo pronto para a Inteligência Artificial!")

if __name__ == "__main__":
    processar_etl()