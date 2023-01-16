package com.fastcampus.tennistdd.practice;

class TennisGameAnnouncingPresenter {
    public String present(final PresentTennisGameRequest request) {
        return switch (request.getStatus()) {
            case STARTED -> presentStarted(request.getServerPoints(), request.getReceiverPoints());
            case DEUCE -> "deuce";
            case ADVANTAGE -> request.getServerPoints() > request.getReceiverPoints() ? "ad-in" : "ad-out";
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

    private String presentPoints(final int points) {
        return switch (points) {
            case 0 -> "love";
            case 1 -> "fifteen";
            case 2 -> "thirty";
            case 3 -> "forty";
            default -> throw new IllegalArgumentException();
        };
    }

    private String presentFinished(int serverPoints, int receiverPoints) {
        return serverPoints > receiverPoints ? "server win!" : "receiver win!";
    }
}
