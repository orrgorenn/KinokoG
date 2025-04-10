package mapleglory.provider.quest.check;

import mapleglory.provider.WzProvider;
import mapleglory.provider.wz.property.WzListProperty;
import mapleglory.world.user.User;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class QuestJobCheck implements QuestCheck {
    private final Set<Integer> jobs;

    public QuestJobCheck(Set<Integer> jobs) {
        this.jobs = jobs;
    }

    @Override
    public boolean check(User user) {
        return jobs.contains(user.getJob());
    }

    public static QuestJobCheck from(WzListProperty jobList) {
        final Set<Integer> jobs = new HashSet<>();
        for (var jobEntry : jobList.getItems().entrySet()) {
            jobs.add(WzProvider.getInteger(jobEntry.getValue()));
        }
        return new QuestJobCheck(
                Collections.unmodifiableSet(jobs)
        );
    }
}
