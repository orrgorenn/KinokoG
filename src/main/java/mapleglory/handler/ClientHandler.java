package mapleglory.handler;

import mapleglory.packet.CustomPacket;
import mapleglory.server.header.InHeader;
import mapleglory.server.header.OutHeader;
import mapleglory.server.migration.MigrationInfo;
import mapleglory.server.migration.TransferInfo;
import mapleglory.server.node.ChannelInfo;
import mapleglory.server.node.Client;
import mapleglory.server.node.LoginServerNode;
import mapleglory.server.packet.InPacket;
import mapleglory.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public final class ClientHandler {
    private static final Logger log = LogManager.getLogger(ClientHandler.class);

    @Handler(InHeader.AliveAck)
    public static void handleAliveAck(Client c, InPacket inPacket) {
    }

    @Handler(InHeader.ExceptionLog)
    public static void handleExceptionLog(Client c, InPacket inPacket) {
        final String data = inPacket.decodeString();
        log.error("Exception log : {}", data);
    }

    @Handler(InHeader.ClientDumpLog)
    public static void handleClientDumpLog(Client c, InPacket inPacket) {
        final short callType = inPacket.decodeShort();
        final int errorType = inPacket.decodeInt();
        final int bufferSize = inPacket.decodeShort();
        inPacket.decodeInt(); // unk

        final short op = inPacket.decodeShort();
        log.error("Error {} | {}({}) | {}", errorType, OutHeader.getByValue(op), Util.opToString(op), inPacket);
    }

    // Custom Packets

    @Handler(InHeader.RegisterMigration)
    public static void handleRegisterMigration(Client c, InPacket inPacket) {
        final int accountId = inPacket.decodeInt();
        final int characterId = inPacket.decodeInt();
        final int channelId = inPacket.decodeInt();
        final byte[] machineId = inPacket.decodeArray(16);
        final byte[] clientKey = c.getClientKey();

        // Resolve target channel
        final LoginServerNode loginServerNode = (LoginServerNode) c.getServerNode();
        final Optional<ChannelInfo> channelInfoResult = loginServerNode.getChannelById(channelId);
        if (channelInfoResult.isEmpty()) {
            log.error("Could not resolve target channel for migration request for character ID : {}", characterId);
            c.write(CustomPacket.registerMigrationResultFail());
            return;
        }

        // Create and submit migration request
        final MigrationInfo migrationInfo = MigrationInfo.from(channelId, accountId, characterId, machineId, clientKey);
        loginServerNode.submitLoginRequest(migrationInfo, (transferResult) -> {
            if (transferResult.isEmpty()) {
                log.error("Failed to submit migration request for character ID : {}", characterId);
                c.write(CustomPacket.registerMigrationResultFail());
                return;
            }

            // Send migration result success
            final TransferInfo transferInfo = transferResult.get();
            c.write(CustomPacket.registerMigrationResultSuccess(transferInfo.getChannelHost(), transferInfo.getChannelPort(), clientKey));
        });
    }
}
