/*********************************************************************/
/**   ACH2002 - Introdução à Análise de Algoritmos                  **/
/**   EACH-USP - Segundo Semestre de 2020                           **/
/**   <2020294> - Prof. Flavio Luiz Coutinho                        **/
/**                                                                 **/
/**   EP1                                                           **/
/**                                                                 **/
/**   <Augusto Xavier Neto>                   <11367581>            **/
/**                                                                 **/
/*********************************************************************/

public class ImageEx extends Image {

	// Criando as variáveis de seno e cosseno de 60 graus que serão úteis para
	// encontrar as coordenadas do ponto B
	double cos60 = Math.cos(3.1415927 / 3.);
	double sen60 = Math.sin(3.1415927 / 3.);

	// A variável reference_rgb vai guardar a cor do pixel passado na função
	// regionFill
	int reference_rgb;

	// Variável para auxiliar na função regionFill
	int n = 0;

	public ImageEx(int w, int h, int r, int g, int b) {

		super(w, h, r, g, b);
	}

	public ImageEx(int w, int h) {

		super(w, h);
	}

	public void kochCurve(int px, int py, int qx, int qy, int l) {

		// Verificar se o limiar é 0, caso seja, apenas traçar uma linha entre os pontos
		// dados.
		if (l == 0) {
			drawLine(px, py, qx, qy);
		} else {
			// Caso seja diferente de 0, vamos diminuir o limiar em 1 (pensando na próxima
			// chamada recursiva da função)
			l--;

			// Encontrando os pontos A e C através da divisão por 3
			double ax = px + (qx - px) / 3.;
			double ay = py + (qy - py) / 3.;
			double cx = qx - (qx - px) / 3.;
			double cy = qy - (qy - py) / 3.;

			// Encontrando o ponto B utilizando seno e cosseno (trigonometria)
			double bx = ax + cos60 * (cx - ax) + sen60 * (cy - ay);
			double by = ay - sen60 * (cx - ax) + cos60 * (cy - ay);

			// Arredondando os números antes de converter para inteiro
			Math.round(ax);
			Math.round(ay);
			Math.round(bx);
			Math.round(by);
			Math.round(cx);
			Math.round(cy);

			// Chamar a função recursivamente para cada "linha reta", assim o processo
			// acontecerá em cada uma delas, e quando o limiar for igual a 0, vai ser
			// desenhado a linha em cada uma delas e sairão da pilha
			kochCurve((int) px, (int) py, (int) ax, (int) ay, l);
			kochCurve((int) ax, (int) ay, (int) bx, (int) by, l);
			kochCurve((int) bx, (int) by, (int) cx, (int) cy, l);
			kochCurve((int) cx, (int) cy, (int) qx, (int) qy, l);
		}
	}

	public void regionFill(int x, int y) {
		// Aqui uso a variável n que somente na primeira chamada, a cor do pixel seja
		// guardada na variável reference_rgb, evitando que esse valor seja trocado em
		// todas as chamadas recursivas
		if (n == 0) {
			reference_rgb = getPixel(x, y);
			n++;
		}

		// Validando as coordenadas passadas, para verificar se estão dentro do limite
		// da imagem
		if (x >= 0 && y >= 0 && x < getWidth() && y < getHeight()) {

			// Verifica se a cor da coordenada atual é a mesma que da coordenada de
			// referência (para a primeira chamada sempre sera true)
			if (getPixel(x, y) == reference_rgb) {
				// Então a cor do pixel atual é alterada
				setPixel(x, y);

				// E é chamado recursivamente para os pixels ao redor
				regionFill(x - 1, y);
				regionFill(x + 1, y);
				regionFill(x, y - 1);
				regionFill(x, y + 1);
			}

		}
	}
}
