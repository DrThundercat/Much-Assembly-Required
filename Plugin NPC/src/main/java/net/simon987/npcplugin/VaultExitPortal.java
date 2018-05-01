package net.simon987.npcplugin;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import net.simon987.server.game.ControllableUnit;
import net.simon987.server.game.GameObject;
import net.simon987.server.game.Location;
import net.simon987.server.logging.LogManager;

/**
 * Final exit portal located in the 'last' World of a Vault dimension
 */
public class VaultExitPortal extends Portal {

    public static final int ID = 9;

    @Override
    public BasicDBObject mongoSerialise() {
        BasicDBObject dbObject = new BasicDBObject();

        dbObject.put("i", getObjectId());
        dbObject.put("x", getX());
        dbObject.put("y", getY());
        dbObject.put("t", ID);
        dbObject.put("dstWorldX", getDestination().worldX);
        dbObject.put("dstWorldY", getDestination().worldY);
        dbObject.put("dstX", getDestination().x);
        dbObject.put("dstY", getDestination().y);
        dbObject.put("dstDimension", getDestination().dimension);

        return dbObject;
    }

    @Override
    public boolean enter(GameObject object) {

        LogManager.LOGGER.info(((ControllableUnit) object).getParent().getUsername() + " Completed vault " +
                object.getWorld().getDimension());

        //todo: save vault completion stat

        return super.enter(object);
    }

    public static Portal deserialize(DBObject obj) {

        VaultExitPortal portal = new VaultExitPortal();

        portal.setDestination(new Location(
                (int) obj.get("dstWorldX"),
                (int) obj.get("dstWorldY"),
                (String) obj.get("dstDimension"),
                (int) obj.get("dstX"),
                (int) obj.get("dstY")));
        portal.setX((int) obj.get("x"));
        portal.setY((int) obj.get("y"));

        return portal;
    }
}
