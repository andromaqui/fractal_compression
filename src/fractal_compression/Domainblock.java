package fractal_compression;

public class Domainblock {

	
	public int[] argb;	
    public float variance;
    public int middleValue;
    
    public boolean isRGB;
    
    public int middleValueR;
    public int middleValueG;
    public int middleValueB;
    
    public float varianceR;
    public float varianceG;
    public float varianceB;


	public Domainblock(int[] argb, boolean isRGB) {
		// creates an empty RasterImage of given size
		this.argb = argb;
		if(!isRGB) 	{
			this.middleValue = setMittelwert(argb);
			this.variance = setVariance(middleValue, argb);
		}
		else {
			this.middleValue = setMittelwert(argb);
			
			this.middleValueR = setMittelwert(extractRGB(argb,0));
			this.varianceR = setVariance(middleValueR, extractRGB(argb,0));

			this.middleValueG = setMittelwert(extractRGB(argb,1));
			this.varianceG = setVariance(middleValueG, extractRGB(argb,1));

			this.middleValueB= setMittelwert(extractRGB(argb,2));
			this.varianceB = setVariance(middleValueB, extractRGB(argb,2));
		}
	}
	
	
	/**
	 * 
	 * @param argbBytes
	 * @param canal
	 * @return
	 */
	public  int[] extractRGB(int[] argbBytes, int canal) {
		
		int[] temp = new int[argbBytes.length];
		
		//red
		if(canal == 0) {
			for(int i=0; i<argbBytes.length;i++) {
				temp[i] = (argbBytes[i] >> 16) & 0xff;
			}
		}
		
		//green
		else if(canal == 1) {
			for(int i=0; i<argbBytes.length;i++) {
				temp[i] = (argbBytes[i] >> 8) & 0xff;
			}
		}
		
		//blue
		else if(canal == 2) {
			for(int i=0; i<argbBytes.length;i++) {
				temp[i] = argbBytes[i]  & 0xff;
			}
		}
		return temp;
	}

	
	
	/**
	 * Gets an array of integers and returns the average value.
	 * 
	 * @param values
	 * @return
	 */
	private  int setMittelwert(int[] values) {
		int sum = 0;
		for (int value : values) {
			sum += value;
		}
		return sum / values.length;
	}
	
	/**
	 * 		
	 * @param mittelWert
	 * @param block
	 * @return
	 */
	public  float setVariance(int middleValue, int[] block) {		
		float varianceDomain = 0;
			for (int i = 0; i < block.length; i++) {
				// subtract average value from current value
				float greyD = block[i] - middleValue;;
				varianceDomain += greyD*greyD;
			
			}
			return varianceDomain;
	}
}
