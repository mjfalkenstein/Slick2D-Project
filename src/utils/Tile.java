package utils;

import org.newdawn.slick.SpriteSheet;

public class Tile {
	
	public float movement;
	public float cover;
	public float protection;
	public float concealment;
	public float damage;
	public float flamability;
	SpriteSheet sprite;
	boolean broken = false;
	TileEnum type;
	
	public Tile(int x, int y, TileEnum e){
		
		switch(e){
		case GRASS:
			createGrassTile();
			break;
		case DIRT:
			createDirtTile();
			break;
		case SAND:
			createSandTile();
			break;
//		case ASH:
//			createAshTile();
//			break;
//		case TALL_GRASS:
//			createTallGrassTile();
//			break;
//		case ROOTS:
//			createRootsTile();
//			break;
//		case TREE:
//			createTreeTile();
//			break;
//		case SAPLING:
//			createSaplingTile();
//			break;
//		case BRUSH:
//			createBrushTile();
//			break;
//		case ACID:
//			createAcidTile();
//			break;
//		case CARPET:
//			createCarpetTile();
//			break;
//		case CERAMIC_OBJECT_L:
//			createCeramicLTile();
//			break;
//		case CERAMIC_OBJECT_M:
//			createCeramicMTile();
//			break;
//		case CERAMIC_OBJECT_S:
//			createCeramicSTile();
//			break;
//		case FOG:
//			createFogTile();
//			break;
//		case ICE:
//			createIceTile();
//			break;
//		case LAVA:
//			createLavaTile();
//			break;
//		case METAL_OBJECT_L:
//			createMetalLTile();
//			break;
//		case METAL_OBJECT_M:
//			createMetalMTile();
//			break;
//		case METAL_OBJECT_S:
//			createMetalSTile();
//			break;
//		case MIASMA:
//			createMiasmaTile();
//			break;
//		case MUD:
//			createMudTile();
//			break;
//		case PAVED_FLOOR:
//			createPavedTile();
//			break;
//		case PERMAFROST:
//			createPermafrostTile();
//			break;
//		case SMOKE:
//			createSmokeTile();
//			break;
//		case SNOW:
//			createSnowTile();
//			break;
//		case STEAM:
//			createSteamTile();
//			break;
//		case STONE_FLOOR:
//			createStoneFloorTile();
//			break;
//		case STONE_OBJECT_L:
//			createStoneLTile();
//			break;
//		case STONE_OBJECT_M:
//			createStoneMTile();
//			break;
//		case STONE_OBJECT_S:
//			createStoneSTile();
//			break;
//		case SWAMP:
//			createSwampTile();
//			break;
//		case TUNDRA:
//			createTundraTile();
//			break;
//		case WATER:
//			createWaterTile();
//			break;
//		case WOOD_FLOOR:
//			createWoodFloorTile();
//			break;
//		case WOOD_OBJECT_L:
//			createWoodLTile();
//			break;
//		case WOOD_OBJECT_M:
//			createWoodMTile();
//			break;
//		case WOOD_OBJECT_S:
//			createWoodSTile();
//			break;
		default:
			break;
		}
	}
		
	private void createGrassTile(){
		movement = 1.0f;
		cover = 0.0f;
		protection = 0.0f;
		concealment = 0.0f;
		damage = 0.0f;
		flamability = 0.01f;
		sprite = new SpriteSheet(sprite, 0, 0);
		type = TileEnum.GRASS;
	}
	
	private void createDirtTile(){
		movement = 1.0f;
		cover = 0.0f;
		protection = 0.0f;
		concealment = 0.0f;
		damage = 0.0f;
		flamability = 0.01f;
		sprite = new SpriteSheet(sprite, 0, 0);
		type = TileEnum.DIRT;
	}
	
	private void createSandTile(){
		movement = 1.0f;
		cover = 0.0f;
		protection = 0.0f;
		concealment = 0.0f;
		damage = 0.0f;
		flamability = 0.01f;
		sprite = new SpriteSheet(sprite, 0, 0);
		type = TileEnum.SAND;
	}
}
