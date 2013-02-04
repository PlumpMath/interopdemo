package com.jentex.logging;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jensmith
 * Date: 03/02/2013
 * Time: 16:24
 * To change this template use File | Settings | File Templates.
 */
public class LogMessageFactory {
    /**
     * Created with IntelliJ IDEA.
     * User: jensmith
     * Date: 03/02/2013
     * Time: 15:58
     * To change this template use File | Settings | File Templates.
     */
    private static class OilLevelsMessage extends LogMessage {
        private final int [] levels;
        private final String tankerId;
        private final Date recordedAt;


        public OilLevelsMessage(int[] levels, String tankerId, Date recordedAt) {
            this.levels = levels;
            this.tankerId = tankerId;
            this.recordedAt = recordedAt;
        }

        @Override
        public String createLogMessage() {
            StringBuilder levelsBuilder = new StringBuilder();
            for (int level : levels) {
                boolean first = levelsBuilder.length()==0;
                if(!first)
                    levelsBuilder.append("-");
                levelsBuilder.append(level);
            }
            return "OIL LEVELS <" + tankerId + ">: " + levelsBuilder.toString();
        }
    }

    public LogMessage oilLevelsMessage(int[] levels, String tankerId, Date recordedAt){
        return new OilLevelsMessage(levels, tankerId, recordedAt);
    }
}
