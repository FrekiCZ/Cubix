package cz.sam.cubix.world.material;

public class Material {
	
	public static final Material air = new Material(0);
	public static final Material grass = new Material(1).setSolid();
	public static final Material dirt = new Material(2).setSolid();
	public static final Material rock = new Material(3).setSolid();
	public static final Material wood = new Material(4).canBurn(true).setSolid();
	public static final Material plank = new Material(5).canBurn(true).setSolid();
	public static final Material cobblestone = new Material(6).canBurn(true).setSolid();
	public static final Material bedrock = new Material(7).setSolid();
	public static final Material sand = new Material(8).setSolid();
	public static final Material sandstone = new Material(9).setSolid();
	
	private boolean canBurn = false;
	private boolean isSolid;
	private int materailID;
	
	public Material(int materialID) {
		this.materailID = materialID;
	}
	
	public Material canBurn(boolean par1) {
		this.canBurn = par1;
		return this;
	}
	
	public Material setSolid() {
		this.isSolid = true;
		return this;
	}
	
	public boolean canBurn() {
		return this.canBurn;
	}
	
	public int getMaterialID() {
		return this.materailID;
	}

	public boolean isSolid() {
		return this.isSolid;
	}
	
}
