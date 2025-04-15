package mapleglory.provider.quest.act;

import mapleglory.packet.world.WvsContext;
import mapleglory.provider.SkillProvider;
import mapleglory.provider.quest.QuestSkillData;
import mapleglory.provider.skill.SkillInfo;
import mapleglory.provider.wz.property.WzListProperty;
import mapleglory.world.skill.SkillConstants;
import mapleglory.world.skill.SkillRecord;
import mapleglory.world.user.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class QuestSkillAct implements QuestAct {
    private final List<QuestSkillData> skills;

    public QuestSkillAct(List<QuestSkillData> skills) {
        this.skills = skills;
    }

    public List<QuestSkillData> getSkills() {
        return skills;
    }

    @Override
    public boolean canAct(User user, int rewardIndex) {
        for (QuestSkillData qsd : skills) {
            if (!qsd.getJobs().contains(user.getJob())) {
                continue;
            }
            final Optional<SkillInfo> skillInfoResult = SkillProvider.getSkillInfoById(qsd.getSkillId());
            if (skillInfoResult.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean doAct(User user, int rewardIndex) {
        for (QuestSkillData qsd : skills) {
            if (!qsd.getJobs().contains(user.getJob())) {
                continue;
            }
            final int skillId = qsd.getSkillId();
            final Optional<SkillInfo> skillInfoResult = SkillProvider.getSkillInfoById(skillId);
            if (skillInfoResult.isEmpty()) {
                return false;
            }
            final SkillRecord skillRecord = new SkillRecord(skillId);
            skillRecord.setSkillLevel(qsd.isOnlyMasterLevel() ? user.getSkillLevel(skillId) : qsd.getSkillLevel());
            skillRecord.setMasterLevel(SkillConstants.isSkillNeedMasterLevel(skillId) ? qsd.getMasterLevel() : 0);
            user.getSkillManager().addSkill(skillRecord);
            user.updatePassiveSkillData();
            user.validateStat();
            user.write(WvsContext.changeSkillRecordResult(skillRecord, false));
        }
        return true;
    }

    public static QuestSkillAct from(WzListProperty skillList) {
        final List<QuestSkillData> skills = QuestSkillData.resolveSkillData(skillList);
        return new QuestSkillAct(
                Collections.unmodifiableList(skills)
        );
    }
}
