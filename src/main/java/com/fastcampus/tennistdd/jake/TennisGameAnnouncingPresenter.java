package com.fastcampus.tennistdd.jake;

class TennisGameAnnouncingPresenter {

    public String present(final PresentTennisGameRequest request) {
        return switch (request.getStatus()) {
            case STARTED -> presentStarted(request.getServerPoints(), request.getReceiverPoints());
            case DEUCE -> "deuce";
            case ADVANTAGE -> presentAdvantage(request.getServerPoints(), request.getReceiverPoints());
            case FINISHED -> presentFinished(request.getServerPoints(), request.getReceiverPoints());
        };
    }

    private String presentStarted(final int serverPoints, final int receiverPoints) {
        return serverPoints == receiverPoints ?
                "%s all".formatted(presentPoints(serverPoints)) :
                "%s %s".formatted(
                        presentPoints(serverPoints),
                        presentPoints(receiverPoints));
    }

    private String presentAdvantage(final int serverPoints, final int receiverPoints) {
        return serverPoints > receiverPoints ? "ad-in" : "ad-out";
    }

    private String presentPoints(final int points) {
        return switch (points) {
            case 0 -> "love";
            case 1 -> "fifteen";
            case 2 -> "thirty";
            case 3 -> "forty";
            default -> throw new IllegalArgumentException();
        };
    }

    private String presentFinished(final int serverPoints, final int receiverPoints) {
        return serverPoints > receiverPoints ? "server win!" : "receiver win!";
    }
}
